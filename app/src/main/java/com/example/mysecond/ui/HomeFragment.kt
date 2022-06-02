package com.example.mysecond.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.mysecond.R
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

    private lateinit var swipeButtonNone: Button
    private lateinit var swipeButtonLike: Button
    private lateinit var swipeButtonPass: Button

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


        swipeButtonNone = binding.btnSwipeNone
        swipeButtonLike = binding.btnSwipeLike
        swipeButtonPass = binding.btnSwipePass

        swipeButtonNone.setOnClickListener {
            cardStackView.swipe()
        }
        swipeButtonLike.setOnClickListener {
            cardStackView.swipe()
        }
        swipeButtonPass.setOnClickListener {
            cardStackView.swipe()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Toast.makeText(context, "[CardStackView] onCardDragging", Toast.LENGTH_SHORT)

        Log.d("CardFragment", "ordinal:" + direction!!.ordinal)
        Log.d("CardFragment", "ratio:" + ratio )
        when ( direction?.ordinal ) {
            0 -> {
                swipeButtonNone.setTextColor(Color.YELLOW)
                swipeButtonLike.setTextColor(Color.WHITE)
                swipeButtonPass.setTextColor(Color.WHITE)
            }
            1 -> {
                swipeButtonNone.setTextColor(Color.WHITE)
                swipeButtonLike.setTextColor(Color.WHITE)
                swipeButtonPass.setTextColor(Color.YELLOW)
            }
            2 -> {
                swipeButtonNone.setTextColor(Color.WHITE)
                swipeButtonLike.setTextColor(Color.YELLOW)
                swipeButtonPass.setTextColor(Color.WHITE)
            }
        }

    }

    override fun onCardSwiped(direction: Direction?) {
        Toast.makeText(context, "[CardStackView] onCardSwiped", Toast.LENGTH_SHORT)
        resetSwipeButton()
    }

    override fun onCardRewound() {
        Toast.makeText(context, "[CardStackView] onCardRewound", Toast.LENGTH_SHORT)
    }

    override fun onCardCanceled() {
        Toast.makeText(context, "[CardStackView] onCardCanceled", Toast.LENGTH_SHORT)
        Log.d("CardFragment", "Card Cancel !!!!!!!!!!!!!!!!")
        resetSwipeButton()
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Toast.makeText(context, "[CardStackView] onCardAppeared", Toast.LENGTH_SHORT)
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Toast.makeText(context, "[CardStackView] onCardDisappeared", Toast.LENGTH_SHORT)
        resetSwipeButton()
    }

    fun resetSwipeButton() {
        swipeButtonNone.setTextColor(Color.WHITE)
        swipeButtonLike.setTextColor(Color.WHITE)
        swipeButtonPass.setTextColor(Color.WHITE)

    }

}