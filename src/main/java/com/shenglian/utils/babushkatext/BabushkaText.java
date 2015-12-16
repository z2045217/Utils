/*
 * Copyright (C) 2014 Henrique Boregio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Henrique Boregio (hboregio@gmail.com)
 */
package com.shenglian.utils.babushkatext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BabushkaText extends TextView {

    private static int DEFAULT_ABSOLUTE_TEXT_SIZE;
    private static float DEFAULT_RELATIVE_TEXT_SIZE = 1;

    private List<Piece> mPieces;

    public BabushkaText(Context context) {
        super(context);
        init();
    }

    public BabushkaText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BabushkaText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPieces = new ArrayList<>();
        BabushkaText.DEFAULT_ABSOLUTE_TEXT_SIZE = (int) getTextSize();
    }

    public void addPiece(Piece aPiece) {
        mPieces.add(aPiece);
    }

    public void addPiece(Piece aPiece, int location) {
        mPieces.add(location, aPiece);
    }

    public void replacePieceAt(int location, Piece newPiece) {
        mPieces.set(location, newPiece);
    }

    public void removePiece(int location) {
        mPieces.remove(location);
    }

    public Piece getPiece(int location) {
        if (location >= 0 && location < mPieces.size()) {
            return mPieces.get(location);
        }

        return null;
    }

    public void display() {

        StringBuilder builder = new StringBuilder();
        for (Piece aPiece : mPieces) {
            builder.append(aPiece.text);
        }
        int cursor = 0;
        SpannableString finalString = new SpannableString(builder.toString());
        for (Piece aPiece : mPieces) {
            applySpannablesTo(aPiece, finalString, cursor, cursor + aPiece.text.length());
            cursor += aPiece.text.length();
        }

        setText(finalString);
    }

    private void applySpannablesTo(Piece aPiece, SpannableString finalString, int start, int end) {

        if (aPiece.subscript) {
            finalString.setSpan(new SubscriptSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (aPiece.superscript) {
            finalString.setSpan(new SuperscriptSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (aPiece.strike) {
            finalString.setSpan(new StrikethroughSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (aPiece.underline) {
            finalString.setSpan(new UnderlineSpan(), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        finalString.setSpan(new StyleSpan(aPiece.style), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        finalString.setSpan(new AbsoluteSizeSpan(aPiece.textSize), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        finalString.setSpan(new RelativeSizeSpan(aPiece.textSizeRelative), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        finalString.setSpan(new ForegroundColorSpan(aPiece.textColor), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (aPiece.backgroundColor != -1) {
            finalString.setSpan(new BackgroundColorSpan(aPiece.backgroundColor), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public void reset() {
        mPieces = new ArrayList<>();
        setText("");
    }

    public void changeTextColor(int textColor) {
        for (Piece mPiece : mPieces) {
            mPiece.setTextColor(textColor);
        }
        display();
    }

    public static class Piece {

        private String text;
        private int textColor;
        private final int textSize;
        private final int backgroundColor;
        private final float textSizeRelative;
        private final int style;
        private final boolean underline;
        private final boolean superscript;
        private final boolean strike;
        private final boolean subscript;

        public Piece(Builder builder) {
            this.text = builder.text;
            this.textSize = builder.textSize;
            this.textColor = builder.textColor;
            this.backgroundColor = builder.backgroundColor;
            this.textSizeRelative = builder.textSizeRelative;
            this.style = builder.style;
            this.underline = builder.underline;
            this.superscript = builder.superscript;
            this.subscript = builder.subscript;
            this.strike = builder.strike;
        }

        public void setText(String text) {
            this.text = text;
        }


        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public static class Builder {

            private final String text;

            private int textSize = DEFAULT_ABSOLUTE_TEXT_SIZE;
            private int textColor = Color.BLACK;
            private int backgroundColor = -1;
            private float textSizeRelative = DEFAULT_RELATIVE_TEXT_SIZE;
            private int style = Typeface.NORMAL;
            private boolean underline = false;
            private boolean strike = false;
            private boolean superscript = false;
            private boolean subscript = false;

            public Builder(String text) {
                this.text = text;
            }

            public Builder textSize(int textSize) {
                this.textSize = textSize;
                return this;
            }

            public Builder textColor(int textColor) {
                this.textColor = textColor;
                return this;
            }

            public Builder backgroundColor(int backgroundColor) {
                this.backgroundColor = backgroundColor;
                return this;
            }

            public Builder textSizeRelative(float textSizeRelative) {
                this.textSizeRelative = textSizeRelative;
                return this;
            }

            public Builder style(int style) {
                this.style = style;
                return this;
            }

            public Builder underline() {
                this.underline = true;
                return this;
            }

            public Builder strike() {
                this.strike = true;
                return this;
            }

            public Builder superscript() {
                this.superscript = true;
                return this;
            }

            public Builder subscript() {
                this.subscript = true;
                return this;
            }

            public Piece build() {
                return new Piece(this);
            }
        }
    }

}