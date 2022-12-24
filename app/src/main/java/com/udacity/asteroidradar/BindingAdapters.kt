package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R.string.not_hazardous_asteroid_image

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription= imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription= imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription= imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription= imageView.context.getString(R.string.not_hazardous_asteroid_image)

    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
@BindingAdapter("image")
fun setImageUrl(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    if (pictureOfDay!=null){
if (pictureOfDay.mediaType=="image"){
    imageView.contentDescription=imageView.context.getString(R.string.image_of_the_day)+  pictureOfDay.title
    pictureOfDay.url.let {
        val imgUri = pictureOfDay.url.toUri().buildUpon().scheme("https").build()
        Picasso.with(imageView.context)
            .load(imgUri)
            .error(R.drawable.placeholder_picture_of_day)
            .into(imageView)

    }}else{
        imageView.setImageResource(R.drawable.placeholder_picture_of_day)
    imageView.contentDescription= imageView.context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    }
}else{
        imageView.setImageResource(R.drawable.placeholder_picture_of_day)
        imageView.contentDescription= imageView.context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)

    }
}
