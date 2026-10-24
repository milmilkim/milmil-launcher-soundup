# milmil-launcher-soundup

milmil-launcher(크레마 팔레트용)를 크레마 사운드업(Android 4.4)에서 돌아가게 옮긴 버전. minSdk 19.

## 사운드업 실기기 확인된 사실 (2026-10-24)

- 기기: Crema Sound Up (`model:CREMA_0660L`, adb serial `CREMA89W024271`)
- Android 4.4.4 (SDK 19), Rockchip rk312x, 펌웨어 v1.0.44
- 화면: 758x1024, 상단바 66px (크레마 커스텀 SystemUI, 불투명 흰색)
- USB 디버깅: 설정 → 기기 정보 화면 맨 아래 가장자리를 좌-우-우-중-좌 터치. 재부팅하면 꺼짐
- adb install은 베리파이어 설정 없이 바로 됨
- API 21+ 호출(`Window#setStatusBarColor`, `GridLayout.spec(int, float)` 등)은 Build.VERSION 분기 필수. `./gradlew lintDebug`의 NewApi로 확인

## 크레마 팔레트 실기기 확인된 사실 (2026-07-07, 원본)

- 기기: Crema Palette (`model:CREMA_PALETTE`)
- Android 14 (SDK 34)
- 화면: 1264x1680, 320dpi (≈ 632x840dp)
- 물리키: 위 = 92 `KEYCODE_PAGE_UP`, 아래 = 93 `KEYCODE_PAGE_DOWN` (`onKeyDown`으로 정상 수신)
- APK 사이드로드 가능. 단, adb 설치 시 기기 베리파이어가 막으므로 최초 1회 다음 설정 필요:
  `adb shell settings put global verifier_verify_adb_installs 0`

## 설계 원칙 (요약)

- Java + platform View/XML만 사용. AndroidX/AppCompat/Kotlin/Compose 금지, 외부 의존성 0개 유지
- 애니메이션/위젯/백그라운드 서비스/네트워크/타이머 없음 (idle 작업 0)
- 텍스트와 선은 완전한 검정, 페이지 전환은 즉시 교체
- targetSdk 28은 의도적 (사이드로드 전용, lint의 ExpiredTargetSdkVersion 검사만 비활성화)
- 저장은 SharedPreferences만 사용

## 빌드

```
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```
