package com.tikhomirov.ticktacktoe.field

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.tikhomirov.ticktacktoe.R
import com.tikhomirov.ticktacktoe.base.extensions.getDpAsPx
import com.tikhomirov.ticktacktoe.domain.models.Move
import com.tikhomirov.ticktacktoe.domain.models.MoveType
import com.tikhomirov.ticktacktoe.domain.withMove
import kotlin.math.min

class FieldView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var gap = 0f
    private var width = 0f
    private var height = 0f
    private var fieldSize = 3

    private var playerMoveType = MoveType.X

    private var moveListener: ((Move) -> Unit)? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#E6E6E6")
        style = Paint.Style.STROKE
        strokeWidth = context.getDpAsPx(4f)
    }
    private val crossPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = context.getDpAsPx(4f)
    }
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = context.getDpAsPx(4f)
    }

    private var moves = mutableListOf<Move>()


    fun setOnMoveListener(listener: (Move) -> Unit) {
        moveListener = listener
    }

    fun setBoardSize(newSize: Int) {
        fieldSize = newSize
        gap = width.div(fieldSize)
        invalidate()
    }

    fun setPlayerMoveType(moveType: MoveType) {
        playerMoveType = moveType
    }

    fun addMoves(vararg move: Move) {
        moves.addAll(move)
        invalidate()
    }

    fun setMoves(newMoves: List<Move>) {
        moves.apply {
            clear()
            addAll(newMoves)
        }
        invalidate()
    }

    init {
        setBackgroundResource(R.drawable.bg_field)
        context
            .obtainStyledAttributes(attrs, R.styleable.FieldView, 0, 0)
            .also { attributes ->
                attributes
                    .getInt(R.styleable.FieldView_fieldSize, -1)
                    .takeIf { it > 0 }
                    ?.let { size -> setBoardSize(size) }
            }
            .apply { recycle() }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = min(height, width)
        updateLayoutParams<ViewGroup.LayoutParams> {
            this.height = size
            this.width = size
        }
        setMeasuredDimension(size, size)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()
        gap = w.toFloat().div(fieldSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLines(canvas)
        drawMoves(canvas)
    }

    private fun drawMoves(canvas: Canvas) {
        for ((x, y, moveType) in moves)
            when (moveType) {
                MoveType.X -> drawCross(x, y, canvas)
                MoveType.O -> drawCircle(x, y, canvas)
            }
    }

    private fun drawCross(x: Int, y: Int, canvas: Canvas) {
        canvas.drawLine(
            x * gap + gap * 0.25f,
            y * gap + gap * 0.25f,
            (x + 1) * gap - gap * 0.25f,
            (y + 1) * gap - gap * 0.25f,
            crossPaint
        )
        canvas.drawLine(
            x * gap + gap * 0.25f,
            (y + 1) * gap - gap * 0.25f,
            (x + 1) * gap - gap * 0.25f,
            y * gap + gap * 0.25f,
            crossPaint
        )
    }

    private fun drawCircle(x: Int, y: Int, canvas: Canvas) {
        canvas.drawCircle(
            x * gap + gap / 2,
            y * gap + gap / 2,
            min(gap, gap) / 2 * 0.75f,
            circlePaint
        )
    }


    private fun drawLines(canvas: Canvas) {
        for (i in 1 until fieldSize) {
            canvas.drawLine(gap * i, 0f, gap * i, height, paint)
            canvas.drawLine(0f, gap * i, width, gap * i, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            processTouch(event.x, event.y)
        }
        return super.onTouchEvent(event)
    }

    private fun processTouch(x: Float, y: Float) {
        val column = x.div(gap).toInt()
        val row = y.div(gap).toInt()
        moves
            .any { it.column == column && it.row == row }
            .takeIf { isExists -> isExists.not() }
            ?.let { column to row withMove playerMoveType }
            ?.let { move ->
                moves.add(move)
                moveListener?.invoke(move)
                invalidate()
            }
    }

}

