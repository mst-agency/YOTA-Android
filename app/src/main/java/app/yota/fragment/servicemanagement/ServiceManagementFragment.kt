package app.yota.fragment.servicemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import app.yota.R
import app.yota.di.scope.ViewModelInject
import app.yota.fragment.BaseFragment
import app.yota.view.MoneyCardView
import app.yota.view.notifications.carousel.NotificationsCarouselView
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
    private lateinit var notificationsCarouselView: NotificationsCarouselView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewLifecycleOwnerLiveData.observe(this, Observer { lifecycleOwner ->
            viewModel.stateLiveData.observe(lifecycleOwner, Observer { state ->
                moneyAppBarView.showIfOrInvisible { state is ServiceManagementViewModel.State.Content }
                moneyCardView.showIfOrInvisible { state is ServiceManagementViewModel.State.Content }
                loadingProgressBar.showIfOrInvisible { state is ServiceManagementViewModel.State.Loading }
            })
            viewModel.accountLiveData.observe(lifecycleOwner, Observer { accountData ->
                moneyAppBarView.setBalance(accountData.money)
                moneyCardView.setBalance(accountData.money)

                moneyCardView.setCardNumber(accountData.cardLastNumber)
                moneyAppBarView.setCardNumber(accountData.cardLastNumber)
            })
            viewModel.notificationsLiveData.observe(lifecycleOwner, Observer { notifications ->
                notificationsCarouselView.setData(notifications)
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
        notificationsCarouselView = view.findViewById(R.id.notifications_carousel_view)

        val cardNumberClickListener = View.OnClickListener {
            viewModel.onCardNumberClick()
        }
        moneyAppBarView.setOnCardNumberCLickListener(cardNumberClickListener)
        moneyCardView.setOnCardNumberCLickListener(cardNumberClickListener)
    }

    companion object {
        fun newInstance() = ServiceManagementFragment()
    }
}