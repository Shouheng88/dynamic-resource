package me.shouheng.dynamicsample.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import me.shouheng.dynamicsample.R
import me.shouheng.dynamicsample.databinding.FragmentSecondBinding
import me.shouheng.dynamicsample.service.SampleService
import me.shouheng.uix.common.anno.DialogPosition
import me.shouheng.uix.common.bean.textStyle
import me.shouheng.uix.widget.dialog.content.SimpleList
import me.shouheng.uix.widget.dialog.content.simpleList
import me.shouheng.uix.widget.dialog.showDialog
import me.shouheng.uix.widget.dialog.title.simpleTitle
import me.shouheng.utils.ktx.drawableOf
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.EmptyViewModel

/** The second fragment. */
class SecondFragment : ViewBindingFragment<EmptyViewModel, FragmentSecondBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.buttonToast.onDebouncedClick {
            toast(R.string.second_hello_content)
        }
        binding.buttonService.onDebouncedClick {
            context?.startService(Intent(activity, SampleService::class.java))
        }
        binding.buttonEditDialog.onDebouncedClick {
            (activity as AppCompatActivity).showDialog("list") {
                position = DialogPosition.POS_BOTTOM
                dialogTitle = simpleTitle {
                    title = stringOf(R.string.second_sample_list_title)
                }
                dialogContent = simpleList {
                    textStyle = textStyle {
                        gravity = Gravity.CENTER
                        size = 14f
                        color = Color.BLACK
                        typeFace = Typeface.BOLD
                    }
                    showIcon = true
                    list = getSimpleListData()
                    onItemSelected = { dialog, item ->
                        toast("${item.id} : ${item.content}")
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    private fun getSimpleListData(): List<SimpleList.Item> {
        return listOf(
            SimpleList.Item(
                0,
                stringOf(R.string.second_sample_list_content_1),
                drawableOf(R.drawable.ic_number_1)
            ),
            SimpleList.Item(
                1,
                stringOf(R.string.second_sample_list_content_2),
                drawableOf(R.drawable.ic_number_2)),
            SimpleList.Item(
                2,
                context!!.getString(R.string.second_sample_list_content_3),
                drawableOf(R.drawable.ic_number_3),
                Gravity.START),
            SimpleList.Item(
                3,
                context!!.getString(R.string.second_sample_list_content_4),
                drawableOf(R.drawable.ic_number_4),
                Gravity.END)
        )
    }
}
