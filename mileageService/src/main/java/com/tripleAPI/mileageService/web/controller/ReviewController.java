package com.tripleAPI.mileageService.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tripleAPI.mileageService.api.dto.EventsRequest;
import com.tripleAPI.mileageService.common.CommonUtil;
import com.tripleAPI.mileageService.domain.Member;
import com.tripleAPI.mileageService.domain.Place;
import com.tripleAPI.mileageService.domain.Review;
import com.tripleAPI.mileageService.domain.enums.Action;
import com.tripleAPI.mileageService.api.service.MemberService;
import com.tripleAPI.mileageService.api.service.PlaceService;
import com.tripleAPI.mileageService.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final MemberService memberService;
    private final PlaceService placeService;
    private final ReviewService reviewService;

    @GetMapping("/homeEventsAPI")
    public String homeEventsAPI(Model model) {
        if(memberService.findMembers().isEmpty() && placeService.findPlaces().isEmpty()) {
            Member member1 = new Member("Lucian");
            Member member2 = new Member("Sena");
            Member member3 = new Member("Thresh");
            Member member4 = new Member("Darius");
            Member member5 = new Member("Samira");

            memberService.join(member1);
            memberService.join(member2);
            memberService.join(member3);
            memberService.join(member4);
            memberService.join(member5);

            Place place1 = new Place("Seoul");
            Place place2 = new Place("Tokyo");
            Place place3 = new Place("Jeju");
            Place place4 = new Place("Laos");
            Place place5 = new Place("Pari");

            placeService.join(place1);
            placeService.join(place2);
            placeService.join(place3);
            placeService.join(place4);
            placeService.join(place5);
        }

        model.addAttribute("memberList", memberService.findMembers());
        model.addAttribute("placeList", placeService.findPlaces());
        model.addAttribute("memberPlaceForm", new MemberPlaceForm());

        return "homeEventsAPI";
    }

    @GetMapping("/homePointListAPI")
    public String home(Model model) {
        if(memberService.findMembers().isEmpty() && placeService.findPlaces().isEmpty()) {
            Member member1 = new Member("Lucian");
            Member member2 = new Member("Sena");
            Member member3 = new Member("Thresh");
            Member member4 = new Member("Darius");
            Member member5 = new Member("Samira");

            memberService.join(member1);
            memberService.join(member2);
            memberService.join(member3);
            memberService.join(member4);
            memberService.join(member5);

            Place place1 = new Place("Seoul");
            Place place2 = new Place("Tokyo");
            Place place3 = new Place("Jeju");
            Place place4 = new Place("Laos");
            Place place5 = new Place("Pari");

            placeService.join(place1);
            placeService.join(place2);
            placeService.join(place3);
            placeService.join(place4);
            placeService.join(place5);
        }

        model.addAttribute("memberList", memberService.findMembers());
        model.addAttribute("memberForm", new MemberForm());

        return "homePointListAPI";
    }

    @PostMapping("/eventsAPIForm")
    public String eventsAPIForm(@Valid MemberPlaceForm memberPlaceForm, BindingResult result, Model model) {

        if(result.hasErrors()) {
            model.addAttribute("memberList", memberService.findMembers());
            model.addAttribute("placeList", placeService.findPlaces());

            return "homeEventsAPI";
        }

        UUID userId = memberPlaceForm.getUserId();
        UUID placeId = memberPlaceForm.getPlaceId();

        Member findMember = memberService.findOne(userId);
        Place findPlace = placeService.findOne(placeId);

        List<Review> findReviews = reviewService.findReviewsByMemberAndPlace(findMember, findPlace);

        model.addAttribute("member", findMember);
        model.addAttribute("place", findPlace);

        //MOD, DELETE
        if(!findReviews.isEmpty()) {
            model.addAttribute("review", findReviews.get(0));
        }
        //ADD
        else {
            model.addAttribute("review", new Review());
            model.addAttribute("action", "ADD");
        }

        return "eventsAPIForm";
    }

    //리뷰 추가 이벤트
    //리뷰 추가에 대한 point 지급 api를 호출함.
    @PostMapping("/addReview")
    public String addReview(ReadyReviewForm readyReviewForm, Model model) throws JsonProcessingException {
        EventsRequest eventsRequest = new EventsRequest();

        //events API request data 설정
        eventsRequest.setType("REVIEW");
        eventsRequest.setAction(Action.ADD);
        eventsRequest.setReviewId(CommonUtil.createSequentialUUID());
        eventsRequest.setContent(readyReviewForm.getContent());
        for(int i = 0; i < readyReviewForm.getPhotoCount(); i++) {
            eventsRequest.getAttachedPhotoIds().add(CommonUtil.createSequentialUUID());
        }
        eventsRequest.setUserId(readyReviewForm.getUserId());
        eventsRequest.setPlaceId(readyReviewForm.getPlaceId());


        //API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EventsRequest> entity = new HttpEntity<EventsRequest>(eventsRequest, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8070/events", HttpMethod.POST, entity, String.class);

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());

        String prettyBody = jsonObject.toString(4);

        model.addAttribute("apiResponse", prettyBody);
        model.addAttribute("myHome", "homeEventsAPI");

        return "apiResponse";
    }

    //리뷰 수정 이벤트
    //리뷰 수정 대한 point 지급 및 회
    @PostMapping("/modReview")
    public String modReview(ReadyReviewForm readyReviewForm, Model model) throws JsonProcessingException {
        EventsRequest eventsRequest = new EventsRequest();

        //events API request data 설정
        eventsRequest.setType("REVIEW");
        eventsRequest.setAction(Action.MOD);
        eventsRequest.setReviewId(readyReviewForm.getReviewId());
        eventsRequest.setContent(readyReviewForm.getContent());
        for(int i = 0; i < readyReviewForm.getPhotoCount(); i++) {
            eventsRequest.getAttachedPhotoIds().add(CommonUtil.createSequentialUUID());
        }
        eventsRequest.setUserId(readyReviewForm.getUserId());
        eventsRequest.setPlaceId(readyReviewForm.getPlaceId());

        //API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EventsRequest> entity = new HttpEntity<EventsRequest>(eventsRequest, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8070/events", HttpMethod.POST, entity, String.class);

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());

        String prettyBody = jsonObject.toString(4);

        model.addAttribute("apiResponse", prettyBody);
        model.addAttribute("myHome", "homeEventsAPI");

        return "apiResponse";
    }

    //리뷰 삭제 이벤트
    //리뷰 삭제에 대한 point 회수
    @PostMapping("/deleteReview")
    public String deleteReview(ReadyReviewForm readyReviewForm, Model model) throws JsonProcessingException {
        EventsRequest eventsRequest = new EventsRequest();
        Review findReview = reviewService.findOne(readyReviewForm.getReviewId());

        //events API request data 설정
        eventsRequest.setType("REVIEW");
        eventsRequest.setAction(Action.DELETE);
        eventsRequest.setReviewId(readyReviewForm.getReviewId());
        eventsRequest.setContent(readyReviewForm.getContent());
        for(int i = 0; i < readyReviewForm.getPhotoCount(); i++) {
            eventsRequest.getAttachedPhotoIds().add(CommonUtil.createSequentialUUID());
        }
        eventsRequest.setUserId(readyReviewForm.getUserId());
        eventsRequest.setPlaceId(readyReviewForm.getPlaceId());

        //API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EventsRequest> entity = new HttpEntity<EventsRequest>(eventsRequest, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8070/events", HttpMethod.POST, entity, String.class);

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());

        String prettyBody = jsonObject.toString(4);

        model.addAttribute("apiResponse", prettyBody);
        model.addAttribute("myHome", "homeEventsAPI");

        return "apiResponse";
    }

    //유저별 포인트 잔액 및 사용이력 조회
    @PostMapping("/pointListAPI")
    public String pointList(@Valid MemberForm memberForm, BindingResult result, Model model) throws JsonProcessingException {
        if(result.hasErrors()) {
            System.out.println("pointList error");
            model.addAttribute("memberList", memberService.findMembers());
            model.addAttribute("placeList", placeService.findPlaces());
            return "homePointListAPI";
        }

        //API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UUID> entity = new HttpEntity<>(memberForm.getUserId(), httpHeaders);

        String pathVariable = String.format("/%s", memberForm.getUserId());
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8070/pointListByUserId" + pathVariable, HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());

        String prettyBody = jsonObject.toString(4);

        model.addAttribute("apiResponse", prettyBody);
        model.addAttribute("myHome", "homePointListAPI");

        return "apiResponse";
    }
}
