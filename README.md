### 목록  

##### [Image Search](#ImageSearch) : 심화 개인 과제  
##### [latecomers 6weeks](#latecomers6weeks) : 후발대 6주차 과제  
##### [AppDevelop deep](#AppDevelopdeep) : 심화 강의  

=================================================================  
  
# ImageSearch  
## 프로그램 설명
- 이 프로젝트는 이미지&동영상을 검색하고 원하는 이미지& 동영상을 보관함에 추가&제거를 할 수 있습니다.
- 검색 결과는 앱 재시작 시 초기화되며, 보관함에 저장된 데이터와 마지막 검색기록은 Sharedpreferences를 이용해서 저장됩니다.
- 처음 검색 결과 데이터와 그 때 보관함에 저장된 데이터 사이에 추가&제거 시 양쪽 모두 갱신됩니다.

## 폴더 및 파일 구조
- `api/` : 애플리케이션의 데이터와 API 호출을 처리합니다.
  - 'ImageData' : 이미지 정보를 저장하는 데이터 클래스
  - 'NetWorkClient' : Retrofit 인스턴스 및 설정
  - 'NetWorkInterface' : Retrofit을 통한 API 호출 인터페이스
  - 'VideoData' : 비디오 정보를 저장하는 데이터 클래스  
  
- `InventoryFragment/` : 보관함 UI와 Adapter가 있습니다.
  - 'InventoryFragment' : 보관함 UI 및 코드
  - 'InventoryFragmentAdapter' : 보관함 화면에서 사용하는 리사이클러뷰 어댑터

- `Method/` : 함수 모음
  - 'Method' : Toast 확장함수 , 시간형식 변환 함수가 있습니다.

- `SearchFragment/` : 검색 UI와 Adapter가 있습니다.
  - 'SearchFragment' : 검색 UI 및 코드
  - 'SearchFragmentAdapter' : 검색 화면에서 사용하는 리사이클러뷰 어댑터

- `SharedPreferences/` : 받아온 데이터를 저장하는 Sharedpreferences 클래스와 해당 데이터클래스가 있습니다.
  - 'SearchData' : API를 통해 서버에서 받아온데이터들을 저장하는 데이터 클래스
  - 'SPF' : SharedPreferences 클래스
  
- `ViewPager2Adapter/` 
  - 'ViewPager2Adapter' : 뷰페이저를 정의한 어댑터 

- `MainActivity` : 탭 레이아웃과 뷰페이저를 적용하는 로직이 있습니다.

## 구조
이 프로젝트는 
    - SearchFragment에서 SearchView를 이용해서 검색합니다.
    - 마지막 검색기록을 저장하고 앱 재실행 시 searchView를 클릭하면 마지막 검색기록이 표시되어 확인 할 수 있습니다.
    - 검색결과를 Sharedpreferences를 이용해서 저장합니다.
    - SearchFragment에서 heart버튼을 클릭해서 보관함에 추가&제거 후 다시 Sharedpreferences를 이용해서 저장합니다.
    - 저장이 되면 InventoryFragment에서 Sharedpreferences를이용해서 getData 후 필터를 이용해서 isLike= true인 데이터만 앱에 표시합니다.
    - InventoryFragment에서 동일하게 heart버튼을 이용해서 추가&제거 후 SearchFragment로 Bundle()을 이용해서 데이터를 업데이트 시켜줍니다.
    - SearchFragment에서는 보관함(InventoryFragment)에서 변경된 사항을 parentFragmentManager를 이용해서 업데이트하고
    - Bundle()로 받아온 데이터와 기존의 데이터를 비교해서 바뀐데이터의 isLike를 false로 수정해주면서 업데이트해서 앱에 표시해줍니다.

=================================================================  
# latecomers6weeks
:후발대 6주차 과제
- Used Code : 생성주기, 확장함수, fragmentManager, viewBinding
=================================================================  
# AppDevelopdeep
:심화 강의

   ㄱ. SharedPreferences 패키지 : SharedPreferences

   		- Used Code : SharedPreferences, 확장함수, viewBinding

   ㄴ. CreatingGoogleMapsAppActivity 패키지 : 구글 지도 앱 만들기

   		- Used Code : OnMapReadyCallback인터페이스, registerForActivityResult, supportFragmentManager, onMapReady, Manifest.permission, onLocationResult, LatLng, CameraUpdateFactory.newCameraPosition

   	ㄷ. miseya 패키지 : 미세먼지 앱 만들기

   		- 예외처리 해결 중 : pm10Value 값이 null일 경우 에러발생

