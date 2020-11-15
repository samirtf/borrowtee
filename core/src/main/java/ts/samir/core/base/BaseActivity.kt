package ts.samir.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseActivity<V : ViewModel?, B : ViewDataBinding?> :
    AppCompatActivity() {
    protected var _viewModel: V? = null
    protected var _binding: B? = null

    @get:LayoutRes
    protected abstract val layout: Int
    protected abstract val viewModel: Class<V>
    protected abstract val viewBinding: B

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    protected fun init() {
        _binding = DataBindingUtil.setContentView(this, layout)
        _viewModel = ViewModelProvider(this).get(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroy()
    }

    protected fun destroy() {
        _binding = null
        _viewModel = null
    }
}