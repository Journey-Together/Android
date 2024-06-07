package kr.tekit.lion.daongil.presentation.schedule

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityScheduleBinding
import kr.tekit.lion.daongil.domain.model.DailyPlan
import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.domain.model.SchedulePlace
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.schedule.adapter.ScheduleImageViewPagerAdapter
import kr.tekit.lion.daongil.presentation.schedule.adapter.ScheduleListAdapter
import kr.tekit.lion.daongil.presentation.schedule.adapter.SchedulePlaceAdapter
import kr.tekit.lion.daongil.presentation.schedule.customview.ReviewReportDialog
import kr.tekit.lion.daongil.presentation.schedule.customview.ScheduleManageBottomSheet
import kr.tekit.lion.daongil.presentation.schedule.customview.ScheduleReviewManageBottomSheet

// 회원 - 본인 - 다가오는 일정
// 회원 - 본인 - 지난 일정 - 리뷰 O
// 회원 - 본인 - 지난 일정 - 리뷰 X (후기 작성 버튼 띄우기)
// 회원 - 타인 - 지난 일정 - 리뷰 O
// 회원 - 타인 - 지난 일정 - 리뷰 X (후기 관련 view 숨기기)
// 비회원 - 타인일정 - 지난 일정 - 리뷰 O (리뷰 신고하기 버튼, 북마크 메뉴에 로그인 팝업 연결)
// 비회원 - 타인일정 - 지난 일정 - 리뷰 X (북마크 메뉴에 로그인 팝업 연결, 리뷰 작성화면 숨기기)
class ScheduleActivity : AppCompatActivity(), SchedulePlaceAdapter.OnSchedulePlaceClickListener,
    ConfirmDialogInterface {
    // 회원-비회원 여부
    private var isUser = true
    // 다가오는 일정 여부
    private var isUpcoming = false
    // 리뷰 작성 여부
    private var reviewState = true
    // 북마크 여부
    private var isBookmarked = false

    // 임시 데이터
    private val place1 = SchedulePlace(0, "장태산자연휴양림", "관광지", listOf(1))
    private val place2 = SchedulePlace(1, "양평군립미술관", "여행지", listOf(1, 2, 4, 5))
    private val place3 = SchedulePlace(2, "태평소국밥", "음식점", listOf(1))
    private val place4 = SchedulePlace(3, "한국보훈복지의료공단 보훈휴양원", "숙박시설", listOf(1))
    private val place5 = SchedulePlace(4, "보물섬남해한우프라자 (남해축협)", "음식점", listOf(1, 2))

    private val day1 = DailyPlan(1, listOf(place1, place2, place3))
    private val day2 = DailyPlan(2, listOf())
    private val day3 = DailyPlan(3, listOf(place4, place5))

    private val schedule = ScheduleDetail(
        0,
        "꼭 가봐야 할 경주 맛집투어",
        "2024-06-01",
        "2024-06-05",
        null,
        true,
        false,
        "멋쟁이",
        listOf("https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210317_54%2F1615960399867iteUS_JPEG%2Fbanner_x.jpg",
            "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150831_262%2F1441013639801FoVs2_JPEG%2F11663971_2.jpg",
            "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20200106_270%2F1578237425235vhzPG_JPEG%2FBtFgbHMA5t8daqoR2j0zHNBL.jpeg.jpg"), // images
        listOf(day1, day2, day3),
        null,
        null,
        null,
    )

    private val schedule2 = ScheduleDetail(
        1,
        "제주 둘레길 여행",
        "2024-06-01",
        "2024-06-05",
        4,
        true,
        false,
        "김사자",
        listOf("https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150831_201%2F1440997727629Eh1lL_JPEG%2F11571707_1.jpg",
            "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150901_90%2F1441045537302H8wax_JPEG%2F156281538832608_4.jpg",
            "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150831_207%2F1440992289833tONYr_JPEG%2F11491637_0.jpg"), // images
        listOf(day1, day2, day3),
        1,
        """안녕하세요. 서울부터 제주도까지 여행하고왔어요. 첫번쨰 사진은 경복궁 사진입니다. 예뻐요. 그리고 제주도 어느 해변가 사진도 찍어왔는데 예뻐요. 그리고 나머지 하나는 제주도 어딘가에서
            |촬영했어요. 맛있는 것도 많이 먹었어요. 제주도에 맛집이 많아서 여행하기 좋았어요. 제가 방문했던 장소들 꼭 다녀오세요. 여행 일정 구경하고 가세요~
        """.trimMargin(),
        null,
    )

    private val binding: ActivityScheduleBinding by lazy {
        ActivityScheduleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initView(schedule2)
        settingScheduleAdapter(schedule2)
    }

    private fun initView(scheduleDetail: ScheduleDetail) {
        with(binding) {
            initToolbarMenu(scheduleDetail.isWriter, scheduleDetail.isPublic)

            // '다녀는 일정' 정보를 볼 때, '남은 기간' 변수에 null 값을 전달해줄 것이라고 가정하고 작성한 코드입니다.
            // 서버에서 전달해주는 값에 따라 코드 수정할게요
            scheduleDetail.daysRemaining?.let {
                textViewScheduleDDay.text = getString(R.string.text_schedule_d_day, it)
                textViewScheduleDDay.visibility = View.VISIBLE
            }

            textViewScheduleName.text = scheduleDetail.title
            textViewSchedulePeriod.text = getString(
                R.string.text_schedule_period,
                scheduleDetail.startDate,
                scheduleDetail.endDate
            )
            if (scheduleDetail.isWriter) {
                textViewScheduleType.text = if (scheduleDetail.isPublic) {
                    getString(R.string.text_schedule_public)
                } else {
                    getString(R.string.text_schedule_private)
                }
            } else {
                textViewScheduleType.text = scheduleDetail.nickname
            }

            initReviewView(scheduleDetail)
            initImageViewPager(scheduleDetail.images)
        }
    }

    private fun initImageViewPager(images : List<String>){
        binding.viewPagerScheduleImages.apply {
            adapter = ScheduleImageViewPagerAdapter(images)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            binding.indicatorScheduleImages.setViewPager(this)
        }
    }

    private fun initToolbarMenu(isWriter: Boolean, isPublic: Boolean) {
        binding.toolbarViewSchedule.apply {
            setNavigationOnClickListener {
                finish()
            }
            if (isWriter) { // 로그인한 사용자의 일정인 경우
                inflateMenu(R.menu.menu_schedule_private)
                setOnMenuItemClickListener {
                    showScheduleManageBottomSheet(isPublic)
                    true
                }

            } else { // 로그인 여부와 상관없이 타인의 일정인 경우
                title = getString(R.string.text_public_schedule)
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
            menuItem.setIcon(R.drawable.bookmark_filled_icon)
        } else {
            menuItem.setIcon(R.drawable.bookmark_icon)
        }
    }

    private fun toggleBookmarkState(menuItem: MenuItem) {
        // 북마크 상태 값 변경
        isBookmarked = !isBookmarked
        // 아이콘 변경
        setBookmarkIcon(menuItem)
    }

    private fun initReviewView(scheduleDetail: ScheduleDetail) {
        // TO DO - daysRemaining!=null : 여행 일정이 "다녀온 일정" 일때만 리뷰 작성 View를 보여준다.
        // daysRemaining 리턴 타입에 맞춰서 코드 수정 필요
        if (scheduleDetail.reviewIdx == null) {
            if (scheduleDetail.isWriter && scheduleDetail.daysRemaining == null) {
                binding.cardViewScheduleEmptyReview.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                scheduleDetail.review?.let { textViewScheduleReviewContent.text = it }
                // TO DO - rating bar에 별점 표기
                cardViewScheduleReview.visibility = View.VISIBLE

                // 사용자 상태에 따라 리뷰 신고하기 or 리뷰 관리버튼 보여준다.
                initReviewMenu(scheduleDetail)
            }
        }
    }

    private fun initReviewMenu(scheduleDetail: ScheduleDetail) {
        if (scheduleDetail.isWriter) {
            binding.imageButtonScheduleManageReview.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    showScheduleReviewManageBottomSheet()
                }
            }
        } else {
            binding.buttonScheduleReportReview.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    if (isUser) {
                        val reviewReportDialog = ReviewReportDialog { reasonType ->
                            // TO DO -> 서버에 신고 내용 접수
                            // 리뷰 idx : scheduleDetail.reviewIdx. 신고 사유: reasonType (Int/String)
                            showSnackBar(binding.toolbarViewSchedule, R.string.text_report_submitted)
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
    }

    private fun settingScheduleAdapter(scheduleDetail: ScheduleDetail) {
        binding.rvScheduleFullList.adapter = ScheduleListAdapter(scheduleDetail.dailyPlans, this)
    }

    override fun onSchedulePlaceClick(placeId: Int) {
        // TO DO - 여행지 상세보기 화면으로 이동
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