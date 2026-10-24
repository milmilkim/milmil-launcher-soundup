package com.milmil.launcher;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 갈무리 픽셀 폰트 적용.
 * 픽셀 폰트는 설계 크기(px)의 정수배에서만 획이 픽셀 격자에 딱 맞으므로,
 * 원래 sp 크기에 가장 가까운 (글꼴, 정수배) 조합을 고르고 안티앨리어싱을 꺼서
 * 회색 번짐 없이 검정 픽셀로만 그린다 (저해상도 e-ink에서 또렷하게).
 */
final class PixelFont {

    /** assets/fonts 의 갈무리 글꼴과 설계 크기(px) */
    private static final String[] FILES = {"fonts/Galmuri11.ttf", "fonts/Galmuri14.ttf"};
    private static final int[] DESIGN_PX = {12, 15};
    private static final int MAX_SCALE = 4;

    private static final Typeface[] typefaces = new Typeface[FILES.length];

    private PixelFont() {
    }

    static void apply(TextView view, float targetSp) {
        Context context = view.getContext();
        float targetPx = targetSp * context.getResources().getDisplayMetrics().scaledDensity;

        int bestFont = 0;
        int bestPx = DESIGN_PX[0];
        for (int i = 0; i < FILES.length; i++) {
            for (int scale = 1; scale <= MAX_SCALE; scale++) {
                int px = DESIGN_PX[i] * scale;
                if (Math.abs(px - targetPx) < Math.abs(bestPx - targetPx)) {
                    bestFont = i;
                    bestPx = px;
                }
            }
        }

        view.setTypeface(typeface(context, bestFont));
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, bestPx);
        view.setPaintFlags((view.getPaintFlags()
                & ~(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG | Paint.LINEAR_TEXT_FLAG)));
    }

    private static Typeface typeface(Context context, int index) {
        if (typefaces[index] == null) {
            typefaces[index] = Typeface.createFromAsset(context.getAssets(), FILES[index]);
        }
        return typefaces[index];
    }
}
