package com.codezcook.archiplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codezcook.archiplanner.Constants
import com.codezcook.archiplanner.data.*
import com.codezcook.archiplanner2.R
import kotlinx.android.synthetic.main.rv_plans.view.*
import kotlinx.android.synthetic.main.rv_qa.view.*
import kotlinx.android.synthetic.main.rv_qatest.view.*
import kotlinx.android.synthetic.main.rv_topic.view.*


class QAAdapter(val test : Int = 0) : RecyclerView.Adapter<QAAdapter.PlaylistViewHolder>() {


    inner class PlaylistViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview)

    private val diffCallback = object : DiffUtil.ItemCallback<QAResponseItem>(){

        override fun areItemsTheSame(oldItem: QAResponseItem, newItem: QAResponseItem): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: QAResponseItem, newItem: QAResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var qaList: List<QAResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((QAResponseItem) -> Unit)? = null


    fun setOnItemClickListener(listener: (QAResponseItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return test
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return  if(viewType == 0){
            PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_qa,parent,false))
        }else{
            PlaylistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_qatest,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return qaList.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val data = qaList[position]
        when (holder.itemViewType) {
            0 -> {
                holder.itemView.apply {
                    val question = "Q: ${data.question}"
                    tv_qa.text = question

                    val answer = "A: ${data.answer}"
                    tv_qaanswerdata.text = answer

                    setOnClickListener {
                        onItemClickListener?.let { click ->
                            click(data)
                        }
                    }
                }
            }
            1 -> {
                holder.itemView.apply {
                    val question = "Q: ${data.question}"
                    tv_question.text = question

                    rb_one.text = data.option1
                    rb_two.text = data.option2
                    rb_three.text = data.option3
                    rb_four.text = data.option4

                    val radiobuttonid = radiogroup.checkedRadioButtonId
                    val radiobutton = this.findViewById<RadioButton>(radiobuttonid)

                    if (data.answer == radiobutton.text){
                        Constants.answerlist.add(position,true)
                    }else{
                        Constants.answerlist.add(position,false)
                    }

                    radiogroup.setOnCheckedChangeListener { group, checkedId ->
                        val radiobutton2 = this.findViewById<RadioButton>(checkedId)
                        Constants.answerlist[position] = data.answer == radiobutton2.text
                    }

                    setOnClickListener {
                        onItemClickListener?.let { click ->
                            click(data)
                        }
                    }
                }
            }
        }

    }

}