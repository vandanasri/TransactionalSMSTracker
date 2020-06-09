package com.example.transactionalsmstracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionalsmstracker.R

class SMSRVAdapter : RecyclerView.Adapter<SMSRVAdapter.SMSViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return SMSViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {

    }


    class SMSViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindData()
        {

        }
    }
}