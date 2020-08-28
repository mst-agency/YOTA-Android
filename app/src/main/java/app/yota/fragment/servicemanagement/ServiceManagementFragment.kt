package app.yota.fragment.servicemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.yota.R
import app.yota.di.scope.ViewModelInject
import app.yota.fragment.BaseFragment
import app.yota.view.MoneyCardView
import app.yota.view.appbar.MoneyAppBarView
import javax.inject.Inject

class ServiceManagementFragment : BaseFragment() {

    @Inject
    @ViewModelInject
    lateinit var viewModel: ServiceManagementViewModel

    lateinit var moneyAppBarView: MoneyAppBarView
    lateinit var moneyCardView: MoneyCardView

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

        moneyAppBarView.setBalance(999.9f)
        moneyAppBarView.setCardNumber(8888)

        moneyCardView.setBalance(999.9f)
        moneyCardView.setCardNumber(8888)
    }

    companion object {
        fun newInstance() = ServiceManagementFragment()
    }
}