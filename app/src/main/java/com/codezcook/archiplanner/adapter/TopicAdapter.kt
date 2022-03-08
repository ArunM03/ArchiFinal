package com.codezcook.archiplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.data.*
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.rv_plans.view.*
import kotlinx.android.synthetic.main.rv_topic.view.*


class TopicAdapter : RecyclerView.Adapter<TopicAdapter.PlaylistViewHolder>() {


    inner class PlaylistViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview)

    private val diffCallback = object : DiffUtil.ItemCallback<TopicDataItem>(){

        override fun areItemsTheSame(oldItem: TopicDataItem, newItem: TopicDataItem): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: TopicDataItem, newItem: TopicDataItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var topicList: List<TopicDataItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((TopicDataItem) -> Unit)? = null


    fun setOnItemClickListener(listener: (TopicDataItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_topic,parent,false))
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val data = topicList[position]
        holder.itemView.apply {

            tv_qatopic.text = data.name

            setOnClickListener {
                onItemClickListener?.let {
                        click ->
                    click(data)
                }
            }
        }
    }



}