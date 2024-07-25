package kr.tekit.lion.daongil.presentation.schedule

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityScheduleBinding
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.schedule.adapter.ScheduleImageViewPagerAdapter
import kr.tekit.lion.daongil.presentation.schedule.adapter.ScheduleListAdapter
import kr.tekit.lion.daongil.presentation.schedule.customview.ReviewReportDialog
import kr.tekit.lion.daongil.presentation.schedule.customview.ScheduleManageBottomSheet
import kr.tekit.lion.daongil.presentation.schedule.customview.ScheduleReviewManageBottomSheet
import kr.tekit.lion.daongil.presentation.schedule.vm.ScheduleDetailViewModel
import kr.tekit.lion.daongil.presentation.schedule.vm.ScheduleDetailViewModelFactory
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.presentation.ext.repeatOnStarted
import kr.tekit.lion.daongil.presentation.ext.setImage
import kr.tekit.lion.daongil.presentation.home.DetailActivity
import kr.tekit.lion.daongil.presentation.login.LogInState
import kr.tekit.lion.daongil.presentation.login.LoginActivity
import kr.tekit.lion.daongil.presentation.schedulereview.WriteScheduleReviewActivity
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

// 회원 - 본인 - 다가오는 일정
// 회원 - 본인 - 지난 일정 - 리뷰 O
// 회원 - 본인 - 지난 일정 - 리뷰 X (후기 작성 버튼 띄우기)
// 회원 - 타인 - 지난 일정 - 리뷰 O
// 회원 - 타인 - 지난 일정 - 리뷰 X (후기 관련 view 숨기기)
// 비회원 - 타인일정 - 지난 일정 - 리뷰 O (리뷰 신고하기 버튼, 북마크 메뉴에 로그인 팝업 연결)
// 비회원 - 타인일정 - 지난 일정 - 리뷰 X (북마크 메뉴에 로그인 팝업 연결, 리뷰 작성화면 숨기기)
class ScheduleDetailActivity : AppCompatActivity(), ConfirmDialogInterface {

    private val viewModel: ScheduleDetailViewModel by viewModels {
        ScheduleDetailViewModelFactory(
            this@ScheduleDetailActivity
        )
    }

    // 북마크 여부
    private var isBookmarked = false

    private val binding: ActivityScheduleBinding by lazy {
        ActivityScheduleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        checkLogin()


    }

    private fun initView(isUser: Boolean) {
        with(binding) {

            viewModel.scheduleDetail.observe(this@ScheduleDetailActivity){ scheduleDetail ->

                initToolbarMenu(isUser, scheduleDetail.isWriter, scheduleDetail.isPublic)

                settingScheduleAdapter(scheduleDetail)

                scheduleDetail.remainDate?.let {
                    scheduleDday.text = it

                    cardViewScheduleEmptyReview.visibility = View.VISIBLE

                    // 지나가지 않은 일정 + 내가 작성자
                    if(scheduleDetail.isWriter){
                        this.scheduleEmptyReviewTitle.text = getString(R.string.text_schedule_info_writer_not_leave_title)
                        this.scheduleEmptyReviewContent.text = getString(R.string.text_schedule_info_writer_not_leave_content)
                        cardViewScheduleEmptyReview.setOnClickListener {
                            val newIntent =
                                Intent(this@ScheduleDetailActivity, WriteScheduleReviewActivity::class.java)
                            val planId = intent.getLongExtra("planId", -1)
                            newIntent.putExtra("planId", planId)
                            startActivity(newIntent)
                        }
                    }
                    // 지나가지 않은 일정 + 내가 작성자가 아님
                    else{
                        this.scheduleEmptyReviewTitle.text = getString(R.string.text_schedule_info_not_leave_title)
                        this.scheduleEmptyReviewContent.text = getString(R.string.text_schedule_info_not_leave_content)
                    }
                } ?: run {
                    // 지나간 일정
                    scheduleDday.visibility = View.GONE

                    // 리뷰가 있을때
                    if(scheduleDetail.hasReview){

                        initReviewMenu(scheduleDetail.isWriter, isUser)

                        cardViewScheduleReview.visibility = View.VISIBLE
                        this@ScheduleDetailActivity.setImage(ivProfileImage, scheduleDetail.profileUrl)
                        textNickname.text = scheduleDetail.nickname
                        ratingBarScheduleSatisfaction.rating = scheduleDetail.grade?.toFloat() ?: 0F
                        textViewScheduleReviewContent.text = scheduleDetail.content

                        when (scheduleDetail.reviewImages?.size) {
                            1 -> {
                                scheduleReviewImg1.visibility = View.VISIBLE
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg1, scheduleDetail.reviewImages[0])
                            }
                            2 -> {
                                scheduleReviewImg1.visibility = View.VISIBLE
                                scheduleReviewImg2.visibility = View.VISIBLE
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg1, scheduleDetail.reviewImages[0])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg2, scheduleDetail.reviewImages[1])
                            }
                            3 -> {
                                scheduleReviewImg1.visibility = View.VISIBLE
                                scheduleReviewImg2.visibility = View.VISIBLE
                                scheduleReviewImg3.visibility = View.VISIBLE
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg1, scheduleDetail.reviewImages[0])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg2, scheduleDetail.reviewImages[1])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg3, scheduleDetail.reviewImages[2])
                            }
                            4 -> {
                                scheduleReviewImg1.visibility = View.VISIBLE
                                scheduleReviewImg2.visibility = View.VISIBLE
                                scheduleReviewImg3.visibility = View.VISIBLE
                                scheduleReviewImg4.visibility = View.VISIBLE
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg1, scheduleDetail.reviewImages[0])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg2, scheduleDetail.reviewImages[1])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg3, scheduleDetail.reviewImages[2])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg4, scheduleDetail.reviewImages[3])
                            }
                            5 -> {
                                scheduleReviewImg1.visibility = View.VISIBLE
                                scheduleReviewImg2.visibility = View.VISIBLE
                                scheduleReviewImg3.visibility = View.VISIBLE
                                scheduleReviewImg4.visibility = View.VISIBLE
                                scheduleReviewImg5.visibility = View.VISIBLE
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg1, scheduleDetail.reviewImages[0])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg2, scheduleDetail.reviewImages[1])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg3, scheduleDetail.reviewImages[2])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg4, scheduleDetail.reviewImages[3])
                                this@ScheduleDetailActivity.setImage(scheduleReviewImg5, scheduleDetail.reviewImages[4])
                            }
                        }

                    }
                    // 리뷰가 없을 때
                    else {
                        if(scheduleDetail.isWriter){
                            cardViewScheduleEmptyReview.visibility = View.VISIBLE
                            this.scheduleEmptyReviewTitle.text = getString(R.string.text_my_review)
                            this.scheduleEmptyReviewContent.text = getString(R.string.text_leave_schedule_review)

                            cardViewScheduleEmptyReview.setOnClickListener {
                                val newIntent =
                                    Intent(this@ScheduleDetailActivity, WriteScheduleReviewActivity::class.java)
                                val planId = intent.getLongExtra("planId", -1)
                                newIntent.putExtra("planId", planId)
                                startActivity(newIntent)
                            }
                        }
                        else {
                            cardViewScheduleEmptyReview.visibility = View.VISIBLE
                            this.scheduleEmptyReviewTitle.text = getString(R.string.text_schedule_info_leave_title)
                            this.scheduleEmptyReviewContent.text = getString(R.string.text_schedule_info_leave_content)
                        }
                    }

                }

                if (scheduleDetail.isPublic) {
                    schedulePublic.text = getString(R.string.text_schedule_public)
                } else {
                    schedulePublic.text = getString(R.string.text_schedule_private)
                }

                textViewScheduleName.text = scheduleDetail.title
                textViewSchedulePeriod.text = getString(
                    R.string.text_schedule_period,
                    scheduleDetail.startDate,
                    scheduleDetail.endDate
                )

                scheduleDetail.images?.let {
                    initImageViewPager(this@ScheduleDetailActivity, it)
                }
            }
        }
    }

    private fun checkLogin(){
        val planId = intent.getLongExtra("planId", -1)

        repeatOnStarted {
            viewModel.loginState.collect { uiState ->
                when (uiState) {
                    is LogInState.Checking -> {
                        return@collect
                    }

                    is LogInState.LoggedIn -> {
                        viewModel.getScheduleDetailInfo(planId)
                        initView(true)
                    }

                    is LogInState.LoginRequired -> {
                        viewModel.getScheduleDetailInfoGuest(planId)
                        initView(false)
                    }
                }
            }
        }
    }

    private fun initImageViewPager(context: Context, images: List<String>) {
        val imageList: List<String> = if (images.isEmpty()) {
            val drawableId = R.drawable.empty_view
            val uri = Uri.parse("android.resource://${context.packageName}/$drawableId").toString()
            listOf(uri)
        } else {
            images
        }

        binding.viewPagerScheduleImages.apply {
            val scheduleAdpater = ScheduleImageViewPagerAdapter(imageList)
            adapter = scheduleAdpater
            startAutoSlide(scheduleAdpater)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

    }

    private fun startAutoSlide(adpater: ScheduleImageViewPagerAdapter) {
        val timer = Timer()
        val handler = Handler(Looper.getMainLooper())

        // 일정 간격으로 슬라이드 변경 (4초마다)
        timer.scheduleAtFixedRate(3000, 4000) {
            handler.post {
                val nextItem = binding.viewPagerScheduleImages.currentItem + 1
                if (nextItem < adpater.itemCount) {
                    binding.viewPagerScheduleImages.currentItem = nextItem
                } else {
                    binding.viewPagerScheduleImages.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }

    private fun initToolbarMenu(isUser: Boolean, isWriter: Boolean, isPublic: Boolean) {
        binding.toolbarViewSchedule.apply {
            setNavigationOnClickListener {
                finish()
            }
            if (isWriter) { // 로그인한 사용자의 일정인 경우
                inflateMenu(R.menu.menu_schedule_private)
                binding.textViewScheduleType.text = getString(R.string.text_my_schedule)
                setOnMenuItemClickListener {
                    showScheduleManageBottomSheet(isPublic)
                    true
                }

            } else { // 로그인 여부와 상관없이 타인의 일정인 경우
                binding.textViewScheduleType.text = getString(R.string.text_public_schedule)
                inflateMenu(R.menu.menu_schedule_public)
                val menuItemBookmark =
                    binding.toolbarViewSchedule.menu.findItem(R.id.menuSchedulPublicBookmark)
                setBookmarkIcon(menuItemBookmark)
                setOnMenuItemClickListener {
                    if (isUser) { // 로그인한 사용자
                        toggleBookmarkState(menuItemBookmark)
                    } else {
                        displayLoginDialog("여행 일정을 북마크하고 싶다면\n로그인을 진행해주세요")
                    }
                    true
                }
            }
        }
    }

    private fun setBookmarkIcon(menuItem: MenuItem) {
        if (isBookmarked) {
            // 임시 아이콘 -> 교체 예정
            menuItem.setIcon(R.drawable.bookmark_fill_scheduledetail_icon)
        } else {
            menuItem.setIcon(R.drawable.bookmark_schedule_detail_icon)
        }
    }

    private fun toggleBookmarkState(menuItem: MenuItem) {
        // 북마크 상태 값 변경
        isBookmarked = !isBookmarked
        // 아이콘 변경
        setBookmarkIcon(menuItem)
    }

    private fun initReviewMenu(isWriter: Boolean, isUser: Boolean) {
        if (isWriter) {
            with(binding.imageButtonScheduleManageReview) {
                visibility = View.VISIBLE
                setOnClickListener {
                    showScheduleReviewManageBottomSheet()
                }
            }
        } else {
            with(binding.buttonScheduleReportReview) {
                visibility = View.VISIBLE
                setOnClickListener {
                    if (isUser) {
                        val reviewReportDialog = ReviewReportDialog { reasonType ->
                            // TO DO -> 서버에 신고 내용 접수
                            // 리뷰 idx : scheduleDetail.reviewIdx. 신고 사유: reasonType (Int/String)
                            showSnackBar(
                                binding.toolbarViewSchedule,
                                R.string.text_report_submitted
                            )
                        }
                        reviewReportDialog.isCancelable = false
                        reviewReportDialog.show(supportFragmentManager, "ModeSettingDialog")
                    } else {
                        displayLoginDialog("여행 후기를 신고하고 싶다면\n로그인을 진행해주세요")
                    }
                }
            }
        }
    }

    private fun displayLoginDialog(subtitle: String) {
        val dialog = ConfirmDialog(
            this,
            "로그인이 필요해요!",
            subtitle,
            "로그인하기",
            R.color.primary,
            R.color.text_primary
        )
        dialog.isCancelable = true
        dialog.show(supportFragmentManager, "ScheduleLoginDialog")
    }

    override fun onPosBtnClick() {
        // TO DO 로그인 화면으로 이동
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun settingScheduleAdapter(scheduleDetail: ScheduleDetail) {
        binding.rvScheduleFullList.adapter = ScheduleListAdapter(scheduleDetail.dailyPlans,
            scheduleListListener = { schedulePosition, placePosition ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("detailPlaceId", scheduleDetail.dailyPlans[schedulePosition].schedulePlaces[placePosition].placeId)
                startActivity(intent)
            })
    }

    private fun showScheduleManageBottomSheet(isPublic: Boolean) {
        ScheduleManageBottomSheet(isPublic) {
            // 공개/비공개 상태 Toggle Listener
            // 공개 -> 비공개
            if (isPublic) {
                // TO DO - 서버에 비공개 상태로 변경 요청
                // TO DO - isPublic 값 업데이트 (이건 어떤 식으로 관리하면 좋을지.. 데이터 연결 후 살펴볼 것)
                binding.textViewScheduleType.text = getString(R.string.text_schedule_private)
                showSnackBar(
                    binding.cardViewScheduleReview,
                    R.string.text_schedule_changed_to_private
                )
            }
            // 비공개 -> 공개
            else {
                // TO DO - 서버에 공개 상태로 변경 요청
                // TO DO - isPublic 값 업데이트 (이건 어떤 식으로 관리하면 좋을지.. 데이터 연결 후 살펴볼 것)
                binding.textViewScheduleType.text = getString(R.string.text_schedule_public)
                showSnackBar(
                    binding.cardViewScheduleReview,
                    R.string.text_schedule_changed_to_public
                )
            }
        }.show(supportFragmentManager, "ScheduleManageBottomSheet")
    }

    private fun showScheduleReviewManageBottomSheet() {
        ScheduleReviewManageBottomSheet {
            // TO DO - 서버에 여행 일정 후기 삭제 요청
            // TO DO - 이 화면에서 관리하고 있는 일정정보 data에도 후기 값 업데이트? (보류)
            binding.cardViewScheduleReview.visibility = View.GONE
            binding.cardViewScheduleEmptyReview.visibility = View.VISIBLE
            showSnackBar(
                binding.imageButtonScheduleManageReview,
                R.string.text_schedule_review_deleted,
            )
        }.show(supportFragmentManager, "ScheduleReviewManageBottomSheet")
    }

    private fun showSnackBar(view: View, message: Int) {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        )
            .setBackgroundTint(ContextCompat.getColor(this, R.color.text_secondary))
            .show()
    }
}