package com.sqsong.wanandroid.ui.search.adapter

import android.content.Context
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.sqsong.wanandroid.R
import com.sqsong.wanandroid.common.holder.LoadingFooterViewHolder
import com.sqsong.wanandroid.common.holder.LoadingFooterViewHolder.LoadingState
import com.sqsong.wanandroid.data.HomeItem
import com.sqsong.wanandroid.ui.home.adapter.HomeItemAdapter
import com.sqsong.wanandroid.util.CommonUtil
import com.sqsong.wanandroid.util.Constants
import com.sqsong.wanandroid.view.CheckableImageView
import com.sqsong.wanandroid.view.LabelView
import javax.inject.Inject

class SearchAdapter @Inject constructor(val context: Context?, private val dataList: MutableList<HomeItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @LoadingState
    private var mLoadingState: Int = 0
    private var mSearchResult: Int = 0
    private var mSearchKey: String? = null
    private val mInflater = LayoutInflater.from(context)
    private var mActionListener: HomeItemAdapter.HomeItemActionListener? = null

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> Constants.ITEM_TYPE_HEADER
            dataList.size + 1 -> Constants.ITEM_TYPE_FOOTER
            else -> Constants.ITEM_TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.ITEM_TYPE_HEADER -> SearchHeaderViewHolder(mInflater.inflate(R.layout.item_search_header, parent, false))
            Constants.ITEM_TYPE_FOOTER -> LoadingFooterViewHolder(mInflater.inflate(R.layout.item_loading_footer, parent, false))
            else -> SearchViewHolder(mInflater.inflate(R.layout.item_knowledge, parent, false))
        }
    }

    override fun getItemCount(): Int = dataList.size + 2

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchHeaderViewHolder -> holder.bindData(mSearchResult)
            is SearchViewHolder -> holder.bindData(dataList[position - 1], position)
            is LoadingFooterViewHolder -> holder.updateLoadingState(mLoadingState)
        }
    }

    fun setSearchResult(result: Int) {
        mSearchResult = result
        notifyItemChanged(0)
    }

    fun updateLoadingState(@LoadingState state: Int) {
        this.mLoadingState = state
        notifyItemChanged(dataList.size + 1)
    }

    fun setHomeItemActionListener(listener: HomeItemAdapter.HomeItemActionListener) {
        this.mActionListener = listener
    }

    fun setSearchKey(key: String?) {
        this.mSearchKey = key
    }

    inner class SearchHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.resultTv)
        @JvmField
        var resultTv: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(result: Int) {
            resultTv?.text = result.toString()
        }

    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.labelView)
        @JvmField
        var labelView: LabelView? = null

        @BindView(R.id.titleTv)
        @JvmField
        var titleTv: TextView? = null

        @BindView(R.id.authorTv)
        @JvmField
        var authorTv: TextView? = null

        @BindView(R.id.timeTv)
        @JvmField
        var timeTv: TextView? = null

        @BindView(R.id.heartRl)
        @JvmField
        var heartRl: RelativeLayout? = null

        @BindView(R.id.heartIv)
        @JvmField
        var heartIv: CheckableImageView? = null

        @BindView(R.id.shareIv)
        @JvmField
        var shareIv: ImageView? = null

        @BindView(R.id.line)
        @JvmField
        var line: View? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindData(homeItem: HomeItem, position: Int) {
            labelView?.visibility = if (homeItem.fresh) View.VISIBLE else View.INVISIBLE
            authorTv?.text = homeItem.author
            timeTv?.text = homeItem.niceDate
            heartIv?.isChecked = homeItem.collect

            titleTv?.text = Html.fromHtml(homeItem.title)
            val title = titleTv?.text.toString()
            val keyArray = mSearchKey?.split(" ")
            if (keyArray != null) {
                if (keyArray.isNotEmpty()) {
                    val spannable = SpannableStringBuilder(title)
                    val color = CommonUtil.getThemeColor(context, R.attr.colorPrimary)
                    for (key in keyArray) {
                        val trimKey = key.trim()
                        var index = title.indexOf(trimKey, 0, true)
                        while (index >= 0) {
                            spannable.setSpan(ForegroundColorSpan(color), index,
                                    index + trimKey.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            index = title.indexOf(key.trim(), index + trimKey.length, true)
                        }
                    }
                    titleTv?.text = spannable
                }
            }

            heartRl?.setOnClickListener {
                mActionListener?.onStarClick(homeItem, position)
            }

            shareIv?.setOnClickListener {
                mActionListener?.onShareClick(homeItem, position)
            }

            itemView.setOnClickListener {
                mActionListener?.onListItemClick(homeItem, position)
            }

            if (position == dataList.size) {
                line?.visibility = View.GONE
            } else {
                line?.visibility = View.VISIBLE
            }
        }


    }

}