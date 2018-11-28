package nvl.com.mvvm.ui.main

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.main_fragment.*
import nvl.com.mvvm.R
import nvl.com.mvvm.databinding.MainFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    val viewModel: MainViewModel.ViewModel by viewModel()

    lateinit var viewBinding: MainFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.viewModel = viewModel
        btnSet.setOnClickListener { }
    }

    fun size(s: String): Int {
        return s.toIntOrNull() ?: 0
    }

}

fun showToast(context: Context) {
    Toast.makeText(context, "hello", Toast.LENGTH_LONG).show()
}