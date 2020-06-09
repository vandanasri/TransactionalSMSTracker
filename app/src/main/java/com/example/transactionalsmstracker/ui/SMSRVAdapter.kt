package com.example.transactionalsmstracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionalsmstracker.R
import com.example.transactionalsmstracker.model.SMSData
import kotlinx.android.synthetic.main.item_view.view.*

class SMSRVAdapter(private val listOfSMS : ArrayList<SMSData>) : RecyclerView.Adapter<SMSRVAdapter.SMSViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return SMSViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfSMS.size
    }

    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {
        holder.bindData(listOfSMS[position])
    }


    class SMSViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindData(data : SMSData)
        {
            itemView.tvTitle.text = data.sender
            itemView.tvDate.text = data.date
            itemView.tvDesc.text = data.messageText
        }
    }
}