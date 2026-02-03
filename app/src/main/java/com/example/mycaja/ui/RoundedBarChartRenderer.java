package com.example.mycaja.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class RoundedBarChartRenderer extends BarChartRenderer {

    private float mRadius = 10f;
    private Paint mBackgroundPaint;

    public RoundedBarChartRenderer(BarDataProvider chart, ChartAnimator animator,
                                   ViewPortHandler viewPortHandler, int backgroundColor) {
        super(chart, animator, viewPortHandler);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(backgroundColor);
    }

    public void setRadius(float radius) {
        this.mRadius = radius;
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());
        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        for (int j = 0; j < buffer.buffer.length; j += 4) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue;

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;

            float left = buffer.buffer[j];
            float top = buffer.buffer[j + 1];
            float right = buffer.buffer[j + 2];
            float bottom = buffer.buffer[j + 3];

            // Fondo gris (barra completa hasta arriba)
            float maxTop = mViewPortHandler.contentTop();
            drawRoundedBar(c, left, maxTop, right, bottom, mBackgroundPaint);

            // Barra de datos con color
            mRenderPaint.setColor(dataSet.getColor(j / 4));
            drawRoundedBar(c, left, top, right, bottom, mRenderPaint);

            if (drawBorder) {
                drawRoundedBar(c, left, top, right, bottom, mBarBorderPaint);
            }
        }
    }

    @Override
    public void drawValues(Canvas c) {
        // Dibujar valores debajo de las barras
        if (!isDrawingValuesAllowed(mChart))
            return;

        for (int i = 0; i < mChart.getBarData().getDataSetCount(); i++) {
            IBarDataSet dataSet = mChart.getBarData().getDataSetByIndex(i);

            if (!shouldDrawValues(dataSet))
                continue;

            // Configurar paint para los valores con roboto_medium 14sp
            mValuePaint.setTextSize(Utils.convertDpToPixel(14f));
            mValuePaint.setTextAlign(Paint.Align.CENTER);
            mValuePaint.setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));

            ValueFormatter formatter = dataSet.getValueFormatter();

            BarBuffer buffer = mBarBuffers[i];

            float phaseY = mAnimator.getPhaseY();

            for (int j = 0; j < buffer.buffer.length; j += 4) {
                float left = buffer.buffer[j];
                float right = buffer.buffer[j + 2];
                float bottom = buffer.buffer[j + 3];

                int entryIndex = j / 4;
                BarEntry entry = dataSet.getEntryForIndex(entryIndex);

                if (entry == null)
                    continue;

                float val = entry.getY();
                String formattedValue = formatter.getBarLabel(entry);

                // Posición X: centro de la barra
                float x = (left + right) / 2f;

                // Posición Y: debajo de la barra
                float y = bottom + Utils.convertDpToPixel(15f);

                // Color del valor igual al de la barra
                mValuePaint.setColor(dataSet.getColor(entryIndex));

                c.drawText(formattedValue, x, y, mValuePaint);
            }
        }
    }

    private void drawRoundedBar(Canvas c, float left, float top, float right, float bottom, Paint paint) {
        Path path = new Path();
        RectF rect = new RectF(left, top, right, bottom);

        // Redondear todas las esquinas (arriba y abajo)
        float[] corners = new float[]{
                mRadius, mRadius,   // Top left
                mRadius, mRadius,   // Top right
                mRadius, mRadius,   // Bottom right
                mRadius, mRadius    // Bottom left
        };

        path.addRoundRect(rect, corners, Path.Direction.CW);
        c.drawPath(path, paint);
    }
}
