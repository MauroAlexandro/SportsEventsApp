package com.mauroalexandro.sportseventsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauroalexandro.sportseventsapp.R
import com.mauroalexandro.sportseventsapp.models.Sport
import com.mauroalexandro.sportseventsapp.models.Sports

/**
 * Created by Mauro_Chegancas
 */
class SportsRecyclerViewAdapter(
    private val context: Context,
) : RecyclerView.Adapter<SportsRecyclerViewAdapter.SportsViewHolder>() {
    private var sportsList = Sports()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        return SportsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.sports_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(sportsViewHolder: SportsViewHolder, position: Int) {
        //Sport
        val sport: Sport = getSportsList(position)

        //Sport Name
        sportsViewHolder.sportName.text = sport.d

        //Events List
        val eventsRecyclerViewAdapter = EventsRecyclerViewAdapter(context)
        eventsRecyclerViewAdapter.setItems(sport.e)
        sportsViewHolder.eventsList.adapter = eventsRecyclerViewAdapter
        sportsViewHolder.eventsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        if(sport.isExpanded) {
            sportsViewHolder.eventsList.visibility = View.VISIBLE
            sportsViewHolder.sportsExpandedToogle.setImageResource(R.mipmap.ic_keyboard_arrow_up_black_24dp)
        } else {
            sportsViewHolder.eventsList.visibility = View.GONE
            sportsViewHolder.sportsExpandedToogle.setImageResource(R.mipmap.ic_keyboard_arrow_down_black_24dp)
        }

        //OnClick
        sportsViewHolder.sportsLayout.setOnClickListener {
            sport.isExpanded = !sport.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return sportsList.size
    }

    private fun getSportsList(position: Int): Sport {
        return sportsList[position]
    }

    fun setSportsList(sportList: List<Sport>) {
        this.sportsList = sportList as Sports
        notifyDataSetChanged()
    }

    class SportsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sportsLayout: ConstraintLayout = view.findViewById(R.id.sports_row_layout)
        val sportsExpandedToogle: ImageView = view.findViewById(R.id.sports_row_expanded_toogle)
        val sportName: TextView = view.findViewById(R.id.sports_row_title)
        val eventsList: RecyclerView = view.findViewById(R.id.sports_row_events_recyclerview)
    }
}