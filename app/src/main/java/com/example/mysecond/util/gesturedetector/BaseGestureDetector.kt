package com.example.mysecond.util.gesturedetector

import android.content.Context
import android.view.MotionEvent




abstract class BaseGestureDetector {
    protected var mContext: Context? = null
    protected var mGestureInProgress = false
    protected var mPrevEvent: MotionEvent? = null
    protected var mCurrEvent: MotionEvent? = null
    protected var mCurrPressure = 0f
    protected var mPrevPressure = 0f
    protected var mTimeDelta: Long = 0

    /**
     * This value is the threshold ratio between the previous combined pressure
     * and the current combined pressure. When pressure decreases rapidly
     * between events the position values can often be imprecise, as it usually
     * indicates that the user is in the process of lifting a pointer off of the
     * device. This value was tuned experimentally.
     */
    protected val PRESSURE_THRESHOLD: Float = 0.67f
    constructor(context: Context) {
        mContext = context
    }

    /**     * All gesture detectors need to be called through this method to be able to
     * detect gestures. This method delegates work to handler methods
     * (handleStartProgressEvent, handleInProgressEvent) implemented in
     * extending classes.
     *
     * @param event
     * @return
     */
    open fun onTouchEvent(event: MotionEvent): Boolean {
        val actionCode = event.action and MotionEvent.ACTION_MASK
        if (!mGestureInProgress) {
            handleStartProgressEvent(actionCode, event)
        } else {
            handleInProgressEvent(actionCode, event)
        }
        return true
    }

    /**
     * Called when the current event occurred when NO gesture is in progress
     * yet. The handling in this implementation may set the gesture in progress
     * (via mGestureInProgress) or out of progress
     * @param actionCode
     * @param event
     */
    protected abstract fun handleStartProgressEvent(actionCode: Int, event: MotionEvent?)
    /**
     * Called when the current event occurred when a gesture IS in progress. The
     * handling in this implementation may set the gesture out of progress (via
     * mGestureInProgress).
     * @param actionCode
     * @param event
     */
    protected abstract fun handleInProgressEvent(actionCode: Int, event: MotionEvent?)
    protected open fun updateStateByEvent(curr: MotionEvent) {
        val prev = mPrevEvent!!
        // Reset mCurrEvent
        if (mCurrEvent != null) {
            mCurrEvent!!.recycle()
            mCurrEvent = null
        }
        mCurrEvent = MotionEvent.obtain(curr) // Delta time
        mTimeDelta = curr.getEventTime() - prev.getEventTime();
        // Pressure
        mCurrPressure = curr.getPressure(curr.actionIndex)
        mPrevPressure = prev.getPressure(prev.actionIndex)
    }

    protected open fun resetState() {
        if (mPrevEvent != null) {
            mPrevEvent!!.recycle()
            mPrevEvent = null
        }
        if (mCurrEvent != null) {
            mCurrEvent!!.recycle()
            mCurrEvent = null
        }
        mGestureInProgress = false
    }

    /**
     * Returns `true` if a gesture is currently in progress.
     * @return `true` if a gesture is currently in progress, `false` otherwise.
     */
    open fun isInProgress(): Boolean {
        return mGestureInProgress
    }

    /**
     * Return the time difference in milliseconds between the previous accepted
     * GestureDetector event and the current GestureDetector event.
     *
     * @return Time difference since the last move event in milliseconds.
     */
    open fun getTimeDelta(): Long {
        return mTimeDelta
    }

    /**
     * Return the event time of the current GestureDetector event being
     * processed.
     *
     * @return Current GestureDetector event time in milliseconds.
     */
    open fun getEventTime(): Long {
        return mCurrEvent!!.eventTime
    }

}
