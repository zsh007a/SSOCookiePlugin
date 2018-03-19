
package com.doctorai.kisso.common.captcha.color;

import java.awt.*;

/**
 * 渐变颜色
 */
public class GradientColorFactory implements ColorFactory {

    private Color start;
    private Color step;

    public GradientColorFactory() {
        start = new Color(192, 192, 0);
        step = new Color(192, 128, 128);
    }

    public Color getColor(int index) {
        return new Color((start.getRed() + step.getRed() * index) % 256,
                (start.getGreen() + step.getGreen() * index) % 256,
                (start.getBlue() + step.getBlue() * index) % 256);
    }

    public void setStart(Color start) {
        this.start = start;
    }

    public void setStep(Color step) {
        this.step = step;
    }

}
