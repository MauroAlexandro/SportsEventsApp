package com.mauroalexandro.sportseventsapp.adapters

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mauroalexandro.sportseventsapp.R
import com.mauroalexandro.sportseventsapp.models.E

/**
 * Created by Mauro_Chegancas
 */
class EventsRecyclerViewAdapter(
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var events: List<E>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.event_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Event
        val event = events[position]

        //Event Time
        getFormattedTime(event.i.toLong(),holder.eventTime)

        //Event Favorite Toogle
        holder.eventFavoriteToggle.setOnClickListener {
            event.favorite = !event.favorite
            setItems(events)
        }

        if (event.favorite)
            holder.eventFavoriteToggle.setImageResource(R.mipmap.ic_star_black_24dp)
        else
            holder.eventFavoriteToggle.setImageResource(R.mipmap.ic_star_border_black_24dp)

        //Event Players
        val players = event.d.split("-")

        //Event Player One
        holder.eventPlayerOne.text = players[0]

        //Event Player Two
        holder.eventPlayerTwo.text = players[1]
    }

    fun setItems(events: List<E>) {
        this.events = events
        this.events = events.sortedBy { !it.favorite }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return events.size
    }

    private fun getFormattedTime(i: Long, eventTime: TextView) {
        object : CountDownTimer(i, 1) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60

                val elapsedHours = millisUntilFinished / hoursInMilli
                val elapsedMinutes = millisUntilFinished % hoursInMilli / minutesInMilli
                val elapsedSeconds =  millisUntilFinished % minutesInMilli / secondsInMilli

                val countDownString = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds)
                eventTime.text = countDownString
            }

            override fun onFinish() {
                eventTime.text = context.resources.getString(R.string.countdown_final)
            }
        }.start()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val eventTime: TextView = view.findViewById(R.id.event_row_time)
    val eventFavoriteToggle: ImageView = view.findViewById(R.id.event_row_favorite_toggle)
    val eventPlayerOne: TextView = view.findViewById(R.id.event_row_playerone)
    val eventPlayerTwo: TextView = view.findViewById(R.id.event_row_playertwo)
}