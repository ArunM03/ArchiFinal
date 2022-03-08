package com.codezcook.archiplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.data.ElevationResponseItem
import com.codezcook.archiplanner.data.ElevationShareData
import com.codezcook.archiplanner.data.PlanResponseItem
import com.codezcook.archiplanner.data.PlanShareData
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.rv_plans.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ElevationAdapter : RecyclerView.Adapter<ElevationAdapter.PlaylistViewHolder>() {


    inner class PlaylistViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview)

    private val diffCallback = object : DiffUtil.ItemCallback<ElevationResponseItem>(){

        override fun areItemsTheSame(
            oldItem: ElevationResponseItem,
            newItem: ElevationResponseItem
        ): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(
            oldItem: ElevationResponseItem,
            newItem: ElevationResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var elevationList: List<ElevationResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((ElevationResponseItem) -> Unit)? = null
    private var onItemClickListener2: ((ElevationShareData) -> Unit)? = null
    private var onItemClickListener3: ((ElevationResponseItem) -> Unit)? = null
    private var onItemClickListener4: ((ElevationResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ElevationResponseItem) -> Unit) {
        onItemClickListener = listener
    }

    fun share(listener: (ElevationShareData) -> Unit) {
        onItemClickListener2 = listener
    }
    fun save(listener: (ElevationResponseItem) -> Unit) {
        onItemClickListener3 = listener
    }
    fun unSave(listener: (ElevationResponseItem) -> Unit) {
        onItemClickListener4 = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_plans,parent,false))
    }

    override fun getItemCount(): Int {
        return elevationList.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val data = elevationList[position]
        holder.itemView.apply {
            tv_plantitle.text = data.title
            val url = "https://admin.archiplanner.in/storage/app/public/${data.folder}/${data.image}"
            Glide.with(context).load(url).into(iv_plandata)
            if (Constants.favtElevationIds.contains(data.id)){
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
                        click(ElevationShareData(data,ct_ivviews))
                    }
                }
            }

            ib_favt.setOnClickListener {
                if (Constants.favtElevationIds.contains(data.id)){
                    Constants.favtElevationIds.remove(data.id)
                    ib_favt.setImageResource(R.drawable.ic_action_unfavt)
                    onItemClickListener4?.let {
                            click ->
                        click(data)
                    }
                }else{
                    Constants.favtElevationIds.add(data.id)
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



}