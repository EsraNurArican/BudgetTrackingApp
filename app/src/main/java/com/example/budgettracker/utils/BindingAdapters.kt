package com.example.budgettracker.utils

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.budgettracker.R
import com.example.budgettracker.data.local.model.TYPE
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("formattedDate")
    fun setFormattedDate(textView: TextView, timestamp: Long?) {
        timestamp?.let {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(it))
            textView.text = formattedDate
        }
    }

    @JvmStatic
    @BindingAdapter("strokeColorForType")
    fun setStrokeColor(cardView: MaterialCardView, type: TYPE) {
        type?.let {
            val color = when (it) {
                TYPE.INCOME -> ContextCompat.getColor(cardView.context, R.color.md_theme_primary)
                TYPE.EXPENSE -> ContextCompat.getColor(cardView.context, R.color.md_theme_error)
            }
            cardView.strokeColor = color
        }
    }

    @JvmStatic
    @BindingAdapter("iconForType")
    fun setTypeIcon(imageView: ImageView, type: TYPE) {
        val drawableRes = when (type) {
            TYPE.INCOME -> R.drawable.arrow_upward
            TYPE.EXPENSE -> R.drawable.arrow_downward
        }
        imageView.setImageResource(drawableRes)
    }


}