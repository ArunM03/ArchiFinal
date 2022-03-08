package com.codezcook.archiplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.data.PlanResponseItem
import com.codezcook.archiplanner.data.PlanShareData
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.rv_plans.view.*
import kotlinx.coroutines.*


class PlanAdapter : RecyclerView.Adapter<PlanAdapter.PlaylistViewHolder>() {


    inner class PlaylistViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview)

    private val diffCallback = object : DiffUtil.ItemCallback<PlanResponseItem>(){

        override fun areItemsTheSame(
            oldItem: PlanResponseItem,
            newItem: PlanResponseItem
        ): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(
            oldItem: PlanResponseItem,
            newItem: PlanResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var planlist: List<PlanResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((PlanResponseItem) -> Unit)? = null
    private var onItemClickListener2: ((PlanShareData) -> Unit)? = null
    private var onItemClickListener3: ((PlanResponseItem) -> Unit)? = null
    private var onItemClickListener4: ((PlanResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (PlanResponseItem) -> Unit) {
        onItemClickListener = listener
    }

    fun share(listener: (PlanShareData) -> Unit) {
        onItemClickListener2 = listener
    }
    fun save(listener: (PlanResponseItem) -> Unit) {
        onItemClickListener3 = listener
    }
    fun unSave(listener: (PlanResponseItem) -> Unit) {
        onItemClickListener4 = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_plans,parent,false))
    }

    override fun getItemCount(): Int {
        return planlist.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val data = planlist[position]
        holder.itemView.apply {
            iv_getitongp.visibility = View.GONE
           data.images?.let {
               val imageslist =    it.split(",")!!.toTypedArray()
               val pos = getPos(imageslist,"b")
               pos?.let {
                   val image = imageslist[pos]
                   val url = "https://admin.archiplanner.in/storage/app/public/${image}"
                   Glide.with(context).load(url).into(iv_plandata)
               }
           }
            tv_plantitle.text = data.name


            if (Constants.favtIds.contains(data.id)){
                ib_favt.setImageResource(R.drawable.ic_action_favt)
            }else{
                ib_favt.setImageResource(R.drawable.ic_action_unfavt)
            }


            ib_share.setOnClickListener {
                iv_getitongp.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.Main).launch{
                    delay(500)
                    onItemClickListener2?.let {
                            click ->
                        click(PlanShareData(data,ct_ivviews))
                    }
                }

            }

            ib_favt.setOnClickListener {
                if (Constants.favtIds.contains(data.id)){
                    Constants.favtIds.remove(data.id)
                    ib_favt.setImageResource(R.drawable.ic_action_unfavt)
                    onItemClickListener4?.let {
                            click ->
                        click(data)
                    }
                }else{
                    Constants.favtIds.add(data.id!!)
                    ib_favt.setImageResource(R.drawable.ic_action_favt)
                    onItemClickListener3?.let {
                            click ->
                        click(data)
                    }
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
    fun getPos(list: Array<String>, type: String) : Int?{
        var newPos :Int? = null
        for (data in list){
            val datalist = data.filter {
                it in type
            }
            if (datalist.isNotEmpty()){
                newPos = list.indexOf(data)
            }
        }
        return newPos
    }



}