package com.example.mysecond.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.PointerCoords
import android.view.MotionEvent.PointerProperties
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import com.example.mysecond.util.gesturedetector.MoveGestureDetector
import com.example.mysecond.util.gesturedetector.RotateGestureDetector

class RotateZoomMoveListener : View {
    enum class FlipDirection {
        NONE, VERTICAL, HORIZONTAL
    }

    private var mScaleDetector: ScaleGestureDetector? = null
    var mScaleFactor = 1f
    private val MAX_SCALE_FACTOR = 30f
    private val MIN_SCALE_FACTOR = 0.3f
    private var mMaxScaleFactor = 30f
    private var mMinScaleFactor = 0.3f
    private var mMoveDetector: MoveGestureDetector? = null
    var mFocusX = 0f
    var mFocusY = 0f
    private var mRotateDetector: RotateGestureDetector? = null
    var mRotationDegree = 0f
    private var mFlipDirection = FlipDirection.NONE
    var mFlipX = 1f
    var mFlipY = 1f
    private var mIsMultiTouch = false
    private var mMoveDistance = 0.0
    private var mTouchPoint = PointF()
    private val MAX_CLICK_DISTANCE = 4
    private val MAX_LONG_CLICK_DISTANCE = 16
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mMaxScaleFactor = MAX_SCALE_FACTOR
        mMinScaleFactor = MIN_SCALE_FACTOR
        mScaleDetector = ScaleGestureDetector(context, getScaleListener())
        mMoveDetector = MoveGestureDetector(context, getMoveListener())
        mRotateDetector = getRotateListener()?.let { RotateGestureDetector(context, it) }
    }

    fun setMaxScaleFactor(max: Float) {
        mMaxScaleFactor = max
    }

    fun setMinScaleFactor(min: Float) {
        mMinScaleFactor = min
    }

    protected fun getScaleListener(): SimpleOnScaleGestureListener? {
        return ScaleListener()
    }

    protected fun getMoveListener(): MoveGestureDetector.SimpleOnMoveGestureListener? {
        return MoveListener()
    }

    protected fun getRotateListener(): RotateGestureDetector.SimpleOnRotateGestureListener? {
        return RotateListener()
    }

    fun setFlip(direction: FlipDirection?) {
        when (direction) {
            FlipDirection.HORIZONTAL -> mFlipX *= -1f
            FlipDirection.VERTICAL -> mFlipY *= -1f
            FlipDirection.NONE -> {
                mFlipX = 1f
                mFlipY = 1f
            }
            else -> {
                mFlipX = 1f
                mFlipY = 1f
            }
        }
        if (mFlipX > 1 && mFlipY > 1) {
            mFlipDirection = FlipDirection.NONE
        } else if (mFlipX > 1 && mFlipY < 1) {
            mFlipDirection = FlipDirection.VERTICAL
        } else if (mFlipX < 1 && mFlipY > 1) {
            mFlipDirection = FlipDirection.HORIZONTAL
        }
        this.invalidate()
    }

    fun getFlipDirection(): FlipDirection? {
        return mFlipDirection
    }

    fun getFlipX(): Float {
        return mFlipX
    }

    fun getFlipY(): Float {
        return mFlipY
    }

    fun getScaleFactor(): Float {
        return mScaleFactor
    }

    fun setScaleFactor(scale: Float) {
        mScaleFactor = scale
        this.invalidate()
    }

    fun setFocus(x: Float, y: Float) {
        mFocusX = x
        mFocusY = y
        this.invalidate()
    }

    fun moveFocus(x: Float, y: Float) {
        setFocus(mFocusX + x, mFocusY + y)
    }

    override fun performClick(): Boolean {
        return if (mIsMultiTouch == true || mMoveDistance > MAX_CLICK_DISTANCE) {
            false
        } else super.performClick()
    }

    override fun performLongClick(): Boolean {
        return if (mIsMultiTouch == true || mMoveDistance > MAX_LONG_CLICK_DISTANCE) {
            false
        } else super.performLongClick()
    }

    private fun getRowPoint(ev: MotionEvent, index: Int, point: PointF) {
        val location = intArrayOf(0, 0)
        getLocationOnScreen(location)
        var x = ev.getX(index)
        var y = ev.getY(index)
        x *= scaleX
        y *= scaleY
        var angle = Math.toDegrees(Math.atan2(y.toDouble(), x.toDouble()))
        angle += rotation.toDouble()
        val length = PointF.length(x, y)
        x = (length * Math.cos(Math.toRadians(angle))).toFloat() + location[0]
        y = (length * Math.sin(Math.toRadians(angle))).toFloat() + location[1]
        point[x] = y
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (this.isEnabled == false) {
            return false
        }
        // compute transfrom
        val prop = arrayOfNulls<PointerProperties>(ev.pointerCount)
        val cords = arrayOfNulls<PointerCoords>(ev.pointerCount)
        val firstCoords = PointerCoords()
        ev.getPointerCoords(0, firstCoords)
        var i = 0
        val n = ev.pointerCount
        while (i < n) {
            val properties = PointerProperties()
            ev.getPointerProperties(i, properties)
            prop[i] = properties
            val cod = PointerCoords()
            ev.getPointerCoords(i, cod)
            val rawPos = PointF()
            getRowPoint(ev, i, rawPos)
            cod.x = rawPos.x
            cod.y = rawPos.y
            cords[i] = cod
            i++
        }
        val screenBaseMotionEvent = MotionEvent.obtain(
            ev.downTime,
            ev.eventTime,
            ev.action,
            ev.pointerCount,
            prop,
            cords,
            ev.metaState,
            ev.buttonState,
            ev.xPrecision,
            ev.yPrecision,
            ev.deviceId,
            ev.edgeFlags,
            ev.source,
            ev.flags
        )
        mScaleDetector!!.onTouchEvent(screenBaseMotionEvent)
        mRotateDetector!!.onTouchEvent(screenBaseMotionEvent)
        mMoveDetector!!.onTouchEvent(screenBaseMotionEvent)
        computeClickEvent(ev) // adjust click event
        super.onTouchEvent(ev)
        this.invalidate()
        return true
    }

    private fun computeClickEvent(ev: MotionEvent) {        // check if it is moved
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mIsMultiTouch = if (ev.pointerCount < 2) false else true
                mTouchPoint = PointF(ev.rawX, ev.rawY)
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (ev.pointerCount > 1) {
                    mIsMultiTouch = true
                    return
                }
                mMoveDistance = getDistance(PointF(ev.rawX, ev.rawY), mTouchPoint)
            }
        }
    }

    private fun getDistance(point1: PointF, point2: PointF): Double {
        return Math.sqrt(
            Math.pow(
                (point1.x - point2.x).toDouble(),
                2.0
            ) + Math.pow((point1.y - point2.y).toDouble(), 2.0)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        this.translationX = mFocusX
        this.translationY = mFocusY
        this.scaleX = mScaleFactor * mFlipX
        this.scaleY = mScaleFactor * mFlipY
        this.rotation = mRotationDegree
        super.onDraw(canvas)
    }

    inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(
            detector: ScaleGestureDetector
        ): Boolean {
            val scaleFactor = detector.scaleFactor
            val mergedScaleFactor: Float = mScaleFactor * scaleFactor
            if (mergedScaleFactor > mMaxScaleFactor || mergedScaleFactor < mMinScaleFactor) {
                return false
            }
            mScaleFactor = mergedScaleFactor
            mScaleFactor = Math.max(mMinScaleFactor, mScaleFactor)
            mScaleFactor = Math.min(mMaxScaleFactor, mScaleFactor)
            return true
        }
    }

    inner class MoveListener : MoveGestureDetector.SimpleOnMoveGestureListener() {
        override fun onMove(detector: MoveGestureDetector?): Boolean {
            val delta: PointF = detector?.getFocusDelta()!!
            mFocusX += delta.x
            mFocusY += delta.y
            return true
        }
    }

    inner class RotateListener : RotateGestureDetector.SimpleOnRotateGestureListener() {
        override fun onRotate(detector: RotateGestureDetector?): Boolean {
            mRotationDegree -= detector!!.getRotationDegreesDelta()
            return true
        }
    }
}