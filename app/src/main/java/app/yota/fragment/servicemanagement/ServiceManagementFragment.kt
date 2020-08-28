package app.yota.fragment.servicemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import app.yota.R
import app.yota.di.scope.ViewModelInject
import app.yota.fragment.BaseFragment
import app.yota.utils.ICurrencyFormatter
import app.yota.view.MoneyCardView
import app.yota.view.appbar.MoneyAppBarView
import app.yota.view.showIfOrInvisible
import javax.inject.Inject

class ServiceManagementFragment : BaseFragment() {

    @Inject
    @ViewModelInject
    lateinit var viewModel: ServiceManagementViewModel

    private lateinit var moneyAppBarView: MoneyAppBarView
    private lateinit var moneyCardView: MoneyCardView
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewLifecycleOwnerLiveData.observe(this, Observer { lifecycleOwner ->
            viewModel.stateLiveData.observe(lifecycleOwner, Observer { state ->
                moneyAppBarView.showIfOrInvisible { state is ServiceManagementViewModel.State.Content }
                moneyCardView.showIfOrInvisible { state is ServiceManagementViewModel.State.Content }
                loadingProgressBar.showIfOrInvisible { state is ServiceManagementViewModel.State.Loading }

                if (state is ServiceManagementViewModel.State.Content) {
                    handleContentState(state)
                }
            })
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service_management, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moneyAppBarView = view.findViewById(R.id.appbar)
        moneyCardView = view.findViewById(R.id.money_card_view)
        loadingProgressBar = view.findViewById(R.id.loading_progress_bar)
    }

    private fun handleContentState(content: ServiceManagementViewModel.State.Content) {
        moneyAppBarView.setBalance(content.moneyFormatted)
        moneyCardView.setBalance(content.moneyFormatted)

        moneyCardView.setCardNumber(content.cardLastNumber)
        moneyAppBarView.setCardNumber(content.cardLastNumber)
    }

    companion object {
        fun newInstance() = ServiceManagementFragment()
    }
}