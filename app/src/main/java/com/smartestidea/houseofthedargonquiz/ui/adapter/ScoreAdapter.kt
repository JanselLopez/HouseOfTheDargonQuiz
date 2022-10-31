package com.smartestidea.houseofthedargonquiz.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartestidea.houseofthedargonquiz.R
import com.smartestidea.houseofthedargonquiz.data.model.Point
import com.smartestidea.houseofthedargonquiz.databinding.CvScoreBinding

class ScoreAdapter(
    private val scores:List<Point>
):RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {
    inner class ScoreViewHolder(val binding: CvScoreBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = CvScoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ScoreViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
       with(holder){
           with(scores[position]){
               binding.apply {
                   cvScore.setCardBackgroundColor(if(position%2==0) R.color.almost_most_dark else R.color.almost_dark)
                   tvPlace.text = (position+1).toString()
                   ivEmblem.setImageResource(emblem)
                   tvPlayer.text = player
                   tvScore.text = points.toString()
               }
           }
       }
    }

    override fun getItemCount(): Int = scores.size
}