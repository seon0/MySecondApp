package com.example.mysecond.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.mysecond.adapter.ProfileStackViewAdapter
import com.example.mysecond.viewmodel.ProfileCardViewModel
import com.example.mysecond.databinding.FragmentHomeBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() , CardStackListener{
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileCardViewModel: ProfileCardViewModel

    private lateinit var profileStackViewAdapter: ProfileStackViewAdapter

    private val cardStackViewManager by lazy {
        CardStackLayoutManager(context, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        profileCardViewModel = ViewModelProvider(this).get(ProfileCardViewModel::class.java)

        profileStackViewAdapter = ProfileStackViewAdapter()
        binding.cardStackView .adapter = profileStackViewAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)

        profileStackViewAdapter.data = profileCardViewModel.list.value!!

        val cardStackView = binding.cardStackView
        cardStackView.layoutManager = cardStackViewManager
        cardStackView.itemAnimator = DefaultItemAnimator()


        binding.chatIconHome.setOnClickListener { view ->
            Log.d("HomeFragment", "rewind before")
            binding.cardStackView.rewind()
            Log.d("HomeFragment", "rewind after")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Toast.makeText(context, "[CardStackView] onCardDragging", Toast.LENGTH_SHORT)
    }

    override fun onCardSwiped(direction: Direction?) {
        Toast.makeText(context, "[CardStackView] onCardSwiped", Toast.LENGTH_SHORT)
    }

    override fun onCardRewound() {
        Toast.makeText(context, "[CardStackView] onCardRewound", Toast.LENGTH_SHORT)
    }

    override fun onCardCanceled() {
        Toast.makeText(context, "[CardStackView] onCardCanceled", Toast.LENGTH_SHORT)
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Toast.makeText(context, "[CardStackView] onCardAppeared", Toast.LENGTH_SHORT)
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Toast.makeText(context, "[CardStackView] onCardDisappeared", Toast.LENGTH_SHORT)
    }


}