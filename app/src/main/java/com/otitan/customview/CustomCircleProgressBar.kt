package com.otitan.customview

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.titan.tianqidemo.R
import com.otitan.util.Utils


class CustomCircleProgressBar : View {

    private val paint = Paint()
    private var rect = Rect()
    private var oval = RectF()
    private var animator: ValueAnimator? = null
    private var progressText: String? = null //圆环内数值文字
    private var title: String? = null//环内标题文字
    private var outsideColor: Int? = null
    private var outsideRadius: Float? = null
    private var insideColor: Int? = null
    private var progressTextColor: Int? = null
    private var progressTextSize: Float? = null
    private var titleSize: Float? = null
    private var progressWidth: Float? = null
    //当前进度
    private var progress: Float? = null
    private var maxProgress: Int? = null
    private var direction: Int? = null

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initAttrs(context, attrs, defStyle)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(context, attrs, 0)
    }

    constructor(context: Context) : super(context)

    private fun initAttrs(context: Context, attrs: AttributeSet, defStyle: Int) {
        val attr = context.theme.obtainStyledAttributes(attrs,
                R.styleable.CustomCircleProgressBar, defStyle, 0)
        outsideColor = attr.getColor(R.styleable.CustomCircleProgressBar_outside_color, ContextCompat.getColor(context, R.color.gray))
        outsideRadius = attr.getDimension(R.styleable.CustomCircleProgressBar_outside_radius, Utils.dp2px(60f, context))
        insideColor = attr.getColor(R.styleable.CustomCircleProgressBar_inside_color, ContextCompat.getColor(context, R.color.colorAccent))
        title = attr.getString(R.styleable.CustomCircleProgressBar_title)
        progressTextColor = attr.getColor(R.styleable.CustomCircleProgressBar_progress_text_color, ContextCompat.getColor(context, R.color.sysGray))
        progressTextSize = attr.getDimension(R.styleable.CustomCircleProgressBar_progress_text_size, Utils.dp2px(16f, context))
        titleSize = attr.getDimension(R.styleable.CustomCircleProgressBar_titleSize, Utils.dp2px(12f, context))
        progressWidth = attr.getDimension(R.styleable.CustomCircleProgressBar_progress_width, Utils.dp2px(10f, context))
        progress = attr.getFloat(R.styleable.CustomCircleProgressBar_progress, 50f)
        maxProgress = attr.getInt(R.styleable.CustomCircleProgressBar_max_progress, 100)
        direction = attr.getInt(R.styleable.CustomCircleProgressBar_direction, 3)
        Log.e("tag", "3")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int
        val height: Int

        var size = MeasureSpec.getSize(widthMeasureSpec)
        var mode = MeasureSpec.getMode(widthMeasureSpec)
        width = if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            ((2 * outsideRadius!!) + progressWidth!!).toInt()
        }

        size = MeasureSpec.getSize(heightMeasureSpec)
        mode = MeasureSpec.getMode(heightMeasureSpec)
        height = if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            ((2 * outsideRadius!!) + progressWidth!!).toInt()
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val circlePoint = width / 2.0f
        //第一步:画背景(即内层圆)
        paint.color = insideColor!! //设置圆的颜色
        paint.style = Paint.Style.STROKE //设置空心
        paint.strokeWidth = progressWidth!! //设置圆的宽度
        paint.isAntiAlias = true  //消除锯齿
        canvas?.drawCircle(circlePoint, circlePoint, outsideRadius!!, paint) //画出圆

        //第二步:画进度(圆弧)
        paint.color = outsideColor!!
        paint.strokeCap = Paint.Cap.ROUND
        oval.left = circlePoint - outsideRadius!!
        oval.top = circlePoint - outsideRadius!!
        oval.right = circlePoint + outsideRadius!!
        oval.bottom = circlePoint + outsideRadius!!
        canvas?.drawArc(oval, CustomCircleProgressBar.DirectionEnum.getDegree(direction!!), 360 * (progress!! / maxProgress!!), false, paint)

        //第三步:画圆环内百分比文字
        paint.color = progressTextColor!!
        paint.textSize = progressTextSize!!
        paint.strokeWidth = 0f
        progressText = getProgressText()
        paint.getTextBounds(progressText, 0, progressText!!.length, rect)
        val fontMetrics = paint.fontMetricsInt
        val baseline = (measuredHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top  //获得文字的基准线
        canvas?.drawText(progressText, measuredWidth / 2f - rect.width() / 2, baseline.toFloat(), paint)

        //第四步:画标题文字
        paint.textSize = titleSize!!
        paint.strokeWidth = 0f
        paint.getTextBounds(title, 0, title!!.length, rect)
        val titleLine = (measuredHeight - fontMetrics.top) / 2 - progressTextSize!! - Utils.dp2px(6f, context)
        canvas?.drawText(title, measuredWidth / 2f - rect.width() / 2, titleLine.toFloat(), paint)
    }

    internal enum class DirectionEnum(val direction: Int, val degree: Float) {
        LEFT(0, 180.0f),
        TOP(1, 270.0f),
        RIGHT(2, 0.0f),
        BOTTOM(3, 90.0f);

        fun equalsDescription(direction: Int): Boolean {
            return this.direction == direction
        }

        companion object {

            fun getDirection(direction: Int): DirectionEnum {
                for (enumObject in values()) {
                    if (enumObject.equalsDescription(direction)) {
                        return enumObject
                    }
                }
                return RIGHT
            }

            fun getDegree(direction: Int): Float {
                val enumObject = getDirection(direction)
                return enumObject.degree
            }
        }
    }

    //中间的文字
    private fun getProgressText(): String {
        return progress?.toInt().toString()
    }


    fun getOutsideColor(): Int? {
        return outsideColor
    }

    fun setOutsideColor(outsideColor: Int) {
        this.outsideColor = outsideColor
    }

    fun getOutsideRadius(): Float? {
        return outsideRadius
    }

    fun setOutsideRadius(outsideRadius: Float) {
        this.outsideRadius = outsideRadius
    }

    fun getInsideColor(): Int? {
        return insideColor
    }

    fun setInsideColor(insideColor: Int) {
        this.insideColor = insideColor
    }

    fun getProgressTextColor(): Int? {
        return progressTextColor
    }

    fun setProgressTextColor(progressTextColor: Int) {
        this.progressTextColor = progressTextColor
    }

    fun getProgressTextSize(): Float? {
        return progressTextSize
    }

    fun setProgressTextSize(progressTextSize: Float) {
        this.progressTextSize = progressTextSize
    }

    fun getProgressWidth(): Float? {
        return progressWidth
    }

    fun setProgressWidth(progressWidth: Float) {
        this.progressWidth = progressWidth
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getTitle(): String? {
        return title
    }

    @Synchronized
    fun getMaxProgress(): Int? {
        return maxProgress
    }

    @Synchronized
    fun setMaxProgress(maxProgress: Int) {
        if (maxProgress < 0) {
            //此为传递非法参数异常
            throw IllegalArgumentException("maxProgress should not be less than 0")
        }
        this.maxProgress = maxProgress
    }

    @Synchronized
    fun getProgress(): Float? {
        return progress
    }

    //加锁保证线程安全,能在线程中使用
    @Synchronized
    fun setProgress(progress: Float) {
        var p = progress
        if (p < 0) {
            throw IllegalArgumentException("progress should not be less than 0")
        }
        if (p > maxProgress!!) {
            p = maxProgress!!.toFloat()
        }
        startAnim(p)
    }

    private fun startAnim(startProgress: Float) {
        animator = ObjectAnimator.ofFloat(0f, startProgress)
        animator?.addUpdateListener { animation ->
            this.progress = animation.animatedValue as Float
            postInvalidate()
        }
//        animator?.startDelay = 500
        animator?.duration = 600
        animator?.interpolator = LinearInterpolator()
        animator?.start()
    }
}