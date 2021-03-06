package app.yota.fragment.servicemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import app.yota.R
import app.yota.di.scope.ViewModelInject
import app.yota.fragment.BaseFragment
import app.yota.view.MoneyCardView
import app.yota.view.ServicesCardView
import app.yota.view.notifications.carousel.NotificationsCarouselView
import app.yota.view.appbar.MoneyAppBarView
import app.yota.view.notifications.carousel.CarouselViewHolder
import app.yota.view.showIfOrHide
import javax.inject.Inject

class ServiceManagementFragment : BaseFragment(), CarouselViewHolder.Listener {

    @Inject
    @ViewModelInject
    lateinit var viewModel: ServiceManagementViewModel

    private lateinit var scrollView: NestedScrollView
    private lateinit var moneyAppBarView: MoneyAppBarView
    private lateinit var moneyCardView: MoneyCardView
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var notificationsCarouselView: NotificationsCarouselView
    private lateinit var servicesCardView: ServicesCardView
    private lateinit var repeatButton: Button
    private lateinit var errorStateGroupView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewLifecycleOwnerLiveData.observe(this, Observer { lifecycleOwner ->
            viewModel.stateLiveData.observe(lifecycleOwner, Observer { state ->
                moneyAppBarView.showIfOrHide { state is ServiceManagementViewModel.State.Content }
                scrollView.showIfOrHide { state is ServiceManagementViewModel.State.Content }
                loadingProgressBar.showIfOrHide { state is ServiceManagementViewModel.State.Loading }
                errorStateGroupView.showIfOrHide { state is ServiceManagementViewModel.State.Error }
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
            viewModel.profileLiveData.observe(lifecycleOwner, Observer { profile ->
                profile.gigabytesAccumulator.let { accumulator ->
                    servicesCardView.setGigabytesAccumulatorDate(accumulator.total, accumulator.current)
                }
                profile.minutesAccumulator.let { accumulator ->
                    servicesCardView.setMinutesAccumulatorDate(accumulator.total, accumulator.current)
                }
                servicesCardView.setTotalAmount(profile.totalAmount)
                servicesCardView.setPhoneNumber(profile.phoneNumber)
                servicesCardView.setNextPaymentDate(profile.nextPaymentDate)
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
        scrollView = view.findViewById(R.id.scroll)
        moneyAppBarView = view.findViewById(R.id.appbar)
        moneyCardView = view.findViewById(R.id.money_card_view)
        loadingProgressBar = view.findViewById(R.id.loading_progress_bar)
        notificationsCarouselView = view.findViewById(R.id.notifications_carousel_view)
        servicesCardView = view.findViewById(R.id.services_card_view)
        errorStateGroupView = view.findViewById(R.id.error_state_group)
        repeatButton = view.findViewById<Button>(R.id.repeat_button).apply {
            setOnClickListener {
                viewModel.onRetryClick()
            }
        }
        servicesCardView.attachToLifecycle(lifecycle)

        val cardNumberClickListener = View.OnClickListener {
            viewModel.onCardNumberClick()
        }
        moneyAppBarView.setOnCardNumberCLickListener(cardNumberClickListener)
        moneyCardView.setOnCardNumberCLickListener(cardNumberClickListener)

        notificationsCarouselView.setCarouselListener(this)
    }

    // region CarouselViewHolder.Listener

    override fun onNotificationActionButtonClick(id: Long) {
        viewModel.onNotificationActionButtonClick(id)
    }

    override fun onNotificationCloseButtonClick(id: Long) {
        viewModel.onNotificationCloseButtonClick(id)
    }

    // endregion CarouselViewHolder.Listener

    companion object {
        fun newInstance() = ServiceManagementFragment()
    }
}