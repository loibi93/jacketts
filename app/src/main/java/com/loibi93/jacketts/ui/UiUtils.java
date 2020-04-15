package com.loibi93.jacketts.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;

public class UiUtils {
    public enum AnimationLength {
        LONG, MEDIUM, SHORT, NONE
    }

    public static void animateVisibilityChange(final View view, final int visibility, AnimationLength animationLength) {
        if (visibility != View.GONE && visibility != View.VISIBLE) {
            throw new IllegalArgumentException("Wrong visibility value");
        }
        if (view.getVisibility() == visibility) {
            return;
        }

        view.setAlpha(visibility == View.GONE ? 1f : 0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(visibility == View.GONE ? 0f : 1f)
                .setDuration(getAnimationDuration(animationLength))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(visibility);
                        view.setAlpha(1);
                    }
                });
    }

    public static void switchViews(final View visibleView, final View invisibleView, final AnimationLength animationLength) {
        visibleView.setAlpha(1);
        invisibleView.setVisibility(View.VISIBLE);
        invisibleView.setAlpha(0);

        final int duration = getAnimationDuration(animationLength);

        visibleView.animate()
                .alpha(0)
                .setDuration((int) (duration / 2f))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        visibleView.setAlpha(1);
                        visibleView.setVisibility(View.GONE);
                        invisibleView.animate()
                                .alpha(1)
                                .setDuration((int) (duration / 2f))
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        invisibleView.setAlpha(1);
                                        invisibleView.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                });
    }

    private static int getAnimationDuration(AnimationLength length) {
        switch (length) {
            case LONG:
                return 1500;
            case MEDIUM:
                return 1000;
            case SHORT:
                return 500;
            case NONE:
                return 0;
        }
        return 0;
    }

    public static String humanReadableByteCount(Long bytes) {
        if (bytes == null) {
            return null;
        }
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format(Locale.ENGLISH, "%.1f %cB", bytes / 1000.0, ci.current());
    }
}
