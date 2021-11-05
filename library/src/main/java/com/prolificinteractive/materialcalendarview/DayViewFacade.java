package com.prolificinteractive.materialcalendarview;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction layer to help in decorating Day views
 */
public class DayViewFacade {

  private boolean isDecorated;
  private MaterialCalendarView mcv;
  @ColorInt
  private Integer textColor = null;
  private Drawable backgroundDrawable = null;
  private Drawable selectionDrawable = null;
  private final LinkedList<Span> spans = new LinkedList<>();
  private boolean daysDisabled = false;
  private boolean selectable;

  DayViewFacade(MaterialCalendarView mcv) {
    isDecorated = false;
    this.mcv = mcv;
  }

  /**
   * Set a drawable to draw behind everything else
   *
   * @param drawable Drawable to draw behind everything
   */
  public void setBackgroundDrawable(@NonNull Drawable drawable) {
    if (drawable == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.backgroundDrawable = drawable;
    isDecorated = true;
  }

  /**
   * Set the color of the text
   *
   * @param color Color of the text
   */
  public void setTextColor(@ColorInt int color) {
    this.textColor = color;
    isDecorated = true;
  }

  /**
   * Set a custom selection drawable
   * TODO: define states that can/should be used in StateListDrawables
   *
   * @param drawable the drawable for selection
   */
  public void setSelectionDrawable(@NonNull Drawable drawable) {
    if (drawable == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    selectionDrawable = drawable;
    isDecorated = true;
  }

  /**
   * Add a span to the entire text of a day
   *
   * @param span text span instance
   */
  public void addSpan(@NonNull Object span) {
    if (spans != null) {
      this.spans.add(new Span(span));
      isDecorated = true;
    }
  }

  /**
   * <p>Set days to be in a disabled state, or re-enabled.</p>
   * <p>Note, passing true here will <b>not</b> override minimum and maximum dates, if set.
   * This will only re-enable disabled dates.</p>
   *
   * @param daysDisabled true to disable days, false to re-enable days
   */
  public void setDaysDisabled(boolean daysDisabled) {
    this.daysDisabled = daysDisabled;
    this.isDecorated = true;
  }

  public void setSelectable(boolean selectable) {
    this.selectable = selectable;
  }

  void reset() {
    textColor = null;
    backgroundDrawable = null;
    selectionDrawable = null;
    spans.clear();
    isDecorated = false;
    daysDisabled = false;
    selectable = mcv.getAllDaysSelectable();
  }

  /**
   * Apply things set this to other
   *
   * @param other facade to apply our data to
   */
  void applyTo(DayViewFacade other) {
    if (textColor != null) {
      other.textColor = textColor;
    }
    if (selectionDrawable != null) {
      other.setSelectionDrawable(selectionDrawable);
    }
    if (backgroundDrawable != null) {
      other.setBackgroundDrawable(backgroundDrawable);
    }
    other.spans.addAll(spans);
    other.isDecorated |= this.isDecorated;
    other.selectable = this.selectable;
    other.daysDisabled = daysDisabled;
  }

  boolean isDecorated() {
    return isDecorated;
  }

  boolean isSelectable() {
    return selectable;
  }

  @Nullable
  @ColorInt
  public Integer getTextColor() {
    return textColor;
  }

  Drawable getSelectionDrawable() {
    return selectionDrawable;
  }

  Drawable getBackgroundDrawable() {
    return backgroundDrawable;
  }

  List<Span> getSpans() {
    return Collections.unmodifiableList(spans);
  }

  /**
   * Are days from this facade disabled
   *
   * @return true if disabled, false if not re-enabled
   */
  public boolean areDaysDisabled() {
    return daysDisabled;
  }

  static class Span {

    final Object span;

    public Span(Object span) {
      this.span = span;
    }
  }
}
