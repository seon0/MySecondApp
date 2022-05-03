package com.example.mysecond.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mysecond.BaseApplication
import com.example.mysecond.adapter.BoardPostAdapter
import com.example.mysecond.data.BoardPost
import com.example.mysecond.databinding.FragmentBoardBinding
import com.example.mysecond.viewmodel.BoardPostViewModel
import com.example.mysecond.viewmodel.BoardPostViewModelFactory

class BoardFragment : Fragment() {

    private var _binding : FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BoardPostViewModel by activityViewModels {
        BoardPostViewModelFactory( (activity?.application as BaseApplication).boardDatabase.boardPostDao() )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.boardPager

        viewPager.clipToPadding = false
//        viewPager.setPadding(100, 0, 100, 0)

        val adapter = BoardPostAdapter(::deleteBoardPost)
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.data = items
            }
        }
        viewPager.adapter = adapter
        viewPager.setPageTransformer(BoardPostingCubeTransformer())

    }

    fun deleteBoardPost(boardPost: BoardPost){
        viewModel.deleteBoardPost(boardPost)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

class BoardPostingCubeTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val deltaY = 0.5F

        page.pivotX = if (position < 0F) page.width.toFloat() else 0F
        page.pivotY = page.height * deltaY
        page.rotationY = 45F * position
    }

}