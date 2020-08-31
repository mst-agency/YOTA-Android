package app.yota.view.valueprogress

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import app.yota.R

internal class ValueProgressDrawable(private val context: Context) : Drawable(), LifecycleObserver {

    private var gradientColors: IntArray = context.resources.getIntArray(R.array.progress_gradient)
    private var rotationColors: IntArray =
        context.resources.getIntArray(R.array.progress_rotation_colors)
    private var rotationAnimator: Animator? = null

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = context.resources.getDimension(R.dimen.gradient_progress_outer_border)
    }

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = context.resources.getDimension(R.dimen.gradient_progress_outer_border)
        color = ContextCompat.getColor(context, R.color.value_progress_background_stoke_color)
    }

    private val rotationPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = context.resources.getDimension(R.dimen.gradient_progress_outer_border)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        updateShader(bounds.width(), bounds.height())
    }

    var angle: Float = 0f

    private var _progress: Float = 0f

    var progress: Float
        get() = _progress
        set(value) {
            when {
                progress > 100 -> {
                    _progress = 100f
                }
                progress < 0 -> {
                    _progress = 0f
                }
                else -> {
                    _progress = value
                }
            }
        }

    override fun draw(canvas: Canvas) {
        val width = bounds.width()
        val height = bounds.height()

        val cx = width / 2f
        val cy = height / 2f

        val archStrokeWidth = progressPaint.strokeWidth
        val strokeHalf = archStrokeWidth / 2
        canvas.drawCircle(cx, cy, width / 2 - strokeHalf, circlePaint)
        canvas.drawArc(
            bounds.left.toFloat() + strokeHalf,
            bounds.top.toFloat() + strokeHalf,
            bounds.right.toFloat() - strokeHalf,
            bounds.bottom.toFloat() - strokeHalf,
            -90f,
            360f * progress / 100,
            false,
            progressPaint
        )

        canvas.save();
        canvas.rotate(angle, cx, cy)
        canvas.drawArc(
            bounds.left.toFloat() + strokeHalf,
            bounds.top.toFloat() + strokeHalf,
            bounds.right.toFloat() - strokeHalf,
            bounds.bottom.toFloat() - strokeHalf,
            0f,
            200f,
            false,
            rotationPaint
        )
        canvas.restore();
    }

    override fun setAlpha(alpha: Int) {
        // not supported
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        // not supported
    }

    override fun getOpacity(): Int = PixelFormat.TRANSPARENT

    private fun updateShader(width: Int, height: Int) {
        progressPaint.shader = SweepGradient(width / 2f, height / 2f, gradientColors, null)
        rotationPaint.shader = SweepGradient(
            width / 2F,
            height / 2F,
            rotationColors,
            floatArrayOf(0.0f, 1.0f)
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        rotationAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
            repeatCount = Animation.INFINITE
            interpolator = LinearInterpolator()
            duration = 1200
            this.addUpdateListener {
                angle = it.animatedValue as Float
                invalidateSelf()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        rotationAnimator?.let { animator ->
            if (animator.isRunning) {
                animator.resume()
            } else {
                animator.start()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onOnPause() {
        rotationAnimator?.let { animator ->
            if (animator.isRunning) {
                animator.pause()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        rotationAnimator?.end()
    }
}