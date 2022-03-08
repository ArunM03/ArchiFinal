package com.codezcook.archiplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.data.PlanFavtShareData
import com.codezcook.archiplanner.data.PlanResponseItem
import com.codezcook.archiplanner.data.PlanShareData
import com.codezcook.archiplanner.data.SavePlanResponseItemItem
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.rv_favtplans.view.*
import kotlinx.android.synthetic.main.rv_plans.view.*
import kotlinx.android.synthetic.main.rv_plans.view.ib_favt
import kotlinx.android.synthetic.main.rv_plans.view.ib_share
import kotlinx.android.synthetic.main.rv_plans.view.iv_plandata


class PlanFavAdapter : RecyclerView.Adapter<PlanFavAdapter.PlaylistViewHolder>() {


    inner class PlaylistViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview)

    private val diffCallback = object : DiffUtil.ItemCallback<SavePlanResponseItemItem>(){

        override fun areItemsTheSame(
            oldItem: SavePlanResponseItemItem,
            newItem: SavePlanResponseItemItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SavePlanResponseItemItem,
            newItem: SavePlanResponseItemItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var planlist: List<SavePlanResponseItemItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((SavePlanResponseItemItem) -> Unit)? = null
    private var onItemClickListener2: ((PlanFavtShareData) -> Unit)? = null
    private var onItemClickListener3: ((SavePlanResponseItemItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (SavePlanResponseItemItem) -> Unit) {
        onItemClickListener = listener
    }

    fun share(listener: (PlanFavtShareData) -> Unit) {
        onItemClickListener2 = listener
    }

    fun unSave(listener: (SavePlanResponseItemItem) -> Unit) {
        onItemClickListener3 = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_favtplans,parent,false))
    }

    override fun getItemCount(): Int {
        return planlist.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val data = planlist[position]
        holder.itemView.apply {
           val imageslist = data.images.split(",").toTypedArray()
            tv_favtplantitle.text = data.name
            val url = "https://admin.archiplanner.in/storage/app/public/${imageslist[0]}"
            Glide.with(context).load(url).into(iv_favtplandata)

            ib_favtshare.setOnClickListener {
                onItemClickListener2?.let {
                        click ->
                    click(PlanFavtShareData(data,iv_plandata))
                }
            }

            ib_unfavt.setOnClickListener {
                Constants.favtIds.remove(data.planid)
                onItemClickListener3?.let {
                        click ->
                    click(data)
                }
            }

            setOnClickListener {
                onItemClickListener?.let {
                        click ->
                    click(data)
                }
            }
        }
    }



}