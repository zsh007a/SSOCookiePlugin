
package com.zsh.kisso.common.captcha.text.renderer;

/**
 * 简单文本渲染
 */
public class SimpleTextRenderer extends AbstractTextRenderer {

    @Override
    protected void arrangeCharacters(int width, int height, TextString ts) {
        double x = leftMargin;
        for (TextCharacter tc : ts.getCharacters()) {
            double y = topMargin + (height + tc.getAscent() * 0.7) / 2;
            tc.setX(x);
            tc.setY(y);
            x += tc.getWidth();
        }
    }

}
