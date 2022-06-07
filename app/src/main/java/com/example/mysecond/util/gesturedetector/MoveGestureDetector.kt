package com.example.mysecond.util.gesturedetector

import android.content.Context
import android.graphics.PointF
import android.view.MotionEvent


class MoveGestureDetector: BaseGestureDetector {
    /**
     * Listener which must be implemented which is used by MoveGestureDetector
     * to perform callbacks to any implementing class which is registered to a
     * MoveGestureDetector via the constructor.
     *
     * @see MoveGestureDetector.SimpleOnMoveGestureListener
     */
    interface OnMoveGestureListener {
        fun onMove(detector: MoveGestureDetector?): Boolean
        fun onMoveBegin(detector: MoveGestureDetector?): Boolean
        fun onMoveEnd(detector: MoveGestureDetector?)
    }
    /**     * Helper class which may be extended and where the methods may be     * implemented. This way it is not necessary to implement all methods     * of OnMoveGestureListener.     */
    open class SimpleOnMoveGestureListener : OnMoveGestureListener {
        override fun onMove(detector: MoveGestureDetector?): Boolean {
            return false
        }

        override fun onMoveBegin(detector: MoveGestureDetector?): Boolean {
            return true
        }

        override fun onMoveEnd(detector: MoveGestureDetector?) {
            // Do nothing, overridden implementation may be used
        }
    }

    private val FOCUS_DELTA_ZERO = PointF()
    private var mListener: OnMoveGestureListener? = null
    private var mCurrFocusInternal: PointF? = null
    private var mPrevFocusInternal: PointF? = null
    private val mFocusExternal = PointF()
    private var mFocusDeltaExternal = PointF()

//    constructor (context: Context?) {
//    }

    constructor(context: Context?, listener: OnMoveGestureListener?) : super(context!!) {
        mListener = listener
    }

    protected override fun handleStartProgressEvent(actionCode: Int, event: MotionEvent?) {
        when (actionCode) {
            MotionEvent.ACTION_DOWN -> {
                resetState() // In case we missed an UP/CANCEL event
                mPrevEvent = MotionEvent.obtain(event)
                mTimeDelta = 0
                event?.let { updateStateByEvent(it) }
            }
            MotionEvent.ACTION_MOVE -> mGestureInProgress = mListener!!.onMoveBegin(this)
        }
    }

    protected override fun handleInProgressEvent(actionCode: Int, event: MotionEvent?) {
        when (actionCode) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mListener!!.onMoveEnd(this)
                resetState()
            }
            MotionEvent.ACTION_MOVE -> {
                if (event != null) {
                    updateStateByEvent(event)
                }
                // Only accept the event if our relative pressure is within
                // a certain limit. This can help filter shaky data as a
                // finger is lifted.
                if (mCurrPressure / mPrevPressure > PRESSURE_THRESHOLD) {
                    val updatePrevious = mListener!!.onMove(this)
                    if (updatePrevious) {
                        mPrevEvent!!.recycle()
                        mPrevEvent = MotionEvent.obtain(event)
                    }
                }
            }
        }
    }

    fun getEvent(): MotionEvent? {
        return mPrevEvent
    }

    protected override fun updateStateByEvent(curr: MotionEvent) {
        super.updateStateByEvent(curr)
        val prev: MotionEvent = mPrevEvent!!
        // Focus intenal
        mCurrFocusInternal = determineFocalPoint(curr)
        mPrevFocusInternal = determineFocalPoint(prev)
        // Focus external
        // - Prevent skipping of focus delta when a finger is added or removed
        val mSkipNextMoveEvent = prev.pointerCount != curr.pointerCount
        mFocusDeltaExternal = if (mSkipNextMoveEvent) FOCUS_DELTA_ZERO else PointF(
            mCurrFocusInternal!!.x - mPrevFocusInternal!!.x,
            mCurrFocusInternal!!.y - mPrevFocusInternal!!.y
        )
        // - Don't directly use mFocusInternal (or skipping will occur). Add
        //      unskipped delta values to mFocusExternal instead.
        mFocusExternal.x += mFocusDeltaExternal.x
        mFocusExternal.y += mFocusDeltaExternal.y
    }

    private fun determineFocalPoint(e: MotionEvent): PointF {
        // Number of fingers on screen
        val pCount = e.pointerCount
        var x = 0f
        var y = 0f
        for (i in 0 until pCount) {
            x += e.getX(i)
            y += e.getY(i)
        }
        return PointF(x / pCount, y / pCount)
    }

    fun getFocusX(): Float {
        return mFocusExternal.x
    }

    fun getFocusY(): Float {
        return mFocusExternal.y
    }

    fun getFocusDelta(): PointF? {
        return mFocusDeltaExternal
    }

}
