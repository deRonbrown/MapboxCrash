# Mapbox Shared Element Transition Crash

Example project demonstrating a crash when using a Mapbox map within a shared element transition.

## Setup
* Set your `MAPBOX_DOWNLOADS_TOKEN` in gradle.properties or as an environment variable.
* Insert your Mapbox token in `MapboxCrashApplication`.
* Run the app

## Transition Info

The shared element transition implementation is roughly based on the following article: https://android-developers.googleblog.com/2018/02/continuous-shared-element-transitions.html

## Crash

When navigating forward and backward between the two fragments, eventually (sometimes much earlier than later) the app will crash with a `signal 6 (SIGABRT)`. I've seen the crash happen on Google Pixels running Android 10 and Android 11. Stacktraces:

```
*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***
Build fingerprint: 'google/sdk_gphone_x86/generic_x86:10/QSR1.190920.001/5891938:user/release-keys'
Revision: '0'
ABI: 'x86'
Timestamp: 2021-01-21 23:03:18-0500
pid: 9958, tid: 10019, name: hwuiTask0  >>> com.xspotlivin.mapboxcrash <<<
uid: 10139
signal 6 (SIGABRT), code -1 (SI_QUEUE), fault addr --------
Abort message: 'decStrong() called on 0xf72ca260 too many times'
    eax 00000000  ebx 000026e6  ecx 00002723  edx 00000006
    edi f638333e  esi c5966830
    ebp f7751ad0  esp c59667d8  eip f7751ad9
backtrace:
      #00 pc 00000ad9  [vdso] (__kernel_vsyscall+9)
      #01 pc 00092328  /apex/com.android.runtime/lib/bionic/libc.so (syscall+40) (BuildId: 76290498408016ad14f4b98c3ab6c65c)
      #02 pc 000ad651  /apex/com.android.runtime/lib/bionic/libc.so (abort+193) (BuildId: 76290498408016ad14f4b98c3ab6c65c)
      #03 pc 00007923  /system/lib/liblog.so (__android_log_assert+307) (BuildId: 93a4440437c37798a143e598876eda6e)
      #04 pc 0000fb52  /system/lib/libutils.so (android::RefBase::decStrong(void const*) const+146) (BuildId: 288ba3aff5b46dbd7e74be954af88b83)
      #05 pc 000c5270  /system/lib/libgui.so (android::SurfaceComposerClient::Transaction::apply(bool)+752) (BuildId: e21258ac2631be8934574fba6dbb8f64)
      #06 pc 00111a9a  /system/lib/libandroid_runtime.so (android::nativeApplyTransaction(_JNIEnv*, _jclass*, long long, unsigned char)+42) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #07 pc 002a20ee  /system/framework/x86/boot-framework.oat (art_jni_trampoline+174) (BuildId: ff6ec03dd8445d20788424c92ba8ea28ad0f54f4)
      #08 pc 0013e9a2  /apex/com.android.runtime/lib/libart.so (art_quick_invoke_static_stub+418) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #09 pc 00149a7a  /apex/com.android.runtime/lib/libart.so (art::ArtMethod::Invoke(art::Thread*, unsigned int*, unsigned int, art::JValue*, char const*)+298) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #10 pc 00332502  /apex/com.android.runtime/lib/libart.so (art::interpreter::ArtInterpreterToCompiledCodeBridge(art::Thread*, art::ArtMethod*, art::ShadowFrame*, unsigned short, art::JValue*)+386) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #11 pc 0032c19c  /apex/com.android.runtime/lib/libart.so (bool art::interpreter::DoCall<false, false>(art::ArtMethod*, art::Thread*, art::ShadowFrame&, art::Instruction const*, unsigned short, art::JValue*)+988) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #12 pc 00684d03  /apex/com.android.runtime/lib/libart.so (MterpInvokeStatic+643) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #13 pc 001389a1  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_static+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #14 pc 00174de8  /system/framework/framework.jar (android.view.SurfaceControl.access$400)
      #15 pc 00684f6c  /apex/com.android.runtime/lib/libart.so (MterpInvokeStatic+1260) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #16 pc 001389a1  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_static+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #17 pc 00173f42  /system/framework/framework.jar (android.view.SurfaceControl$Transaction.apply+10)
      #18 pc 00681adc  /apex/com.android.runtime/lib/libart.so (MterpInvokeVirtual+1612) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #19 pc 00138821  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_virtual+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #20 pc 00173f1e  /system/framework/framework.jar (android.view.SurfaceControl$Transaction.apply+2)
      #21 pc 00681adc  /apex/com.android.runtime/lib/libart.so (MterpInvokeVirtual+1612) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #22 pc 00138821  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_virtual+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #23 pc 00176ab8  /system/framework/framework.jar (android.view.SurfaceView.setParentSpaceRectangle+36)
      #24 pc 006845ac  /apex/com.android.runtime/lib/libart.so (MterpInvokeDirect+1324) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #25 pc 00138921  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_direct+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #26 pc 00176560  /system/framework/framework.jar (android.view.SurfaceView.access$200)
      #27 pc 00684f6c  /apex/com.android.runtime/lib/libart.so (MterpInvokeStatic+1260) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #28 pc 001389a1  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_static+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #29 pc 00175d52  /system/framework/framework.jar (android.view.SurfaceView$3.positionChanged+142)
      #30 pc 006873b9  /apex/com.android.runtime/lib/libart.so (MterpInvokeInterfaceRange+1641) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #31 pc 00138d21  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_interface_range+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #32 pc 00380d40  /system/framework/framework.jar (android.graphics.RenderNode$CompositePositionUpdateListener.positionChanged+36)
      #33 pc 002f8e0a  /apex/com.android.runtime/lib/libart.so (_ZN3art11interpreterL7ExecuteEPNS_6ThreadERKNS_20CodeItemDataAccessorERNS_11ShadowFrameENS_6JValueEbb.llvm.12194892193087984976+298) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #34 pc 002ffcc5  /apex/com.android.runtime/lib/libart.so (art::interpreter::EnterInterpreterFromEntryPoint(art::Thread*, art::CodeItemDataAccessor const&, art::ShadowFrame*)+181) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #35 pc 0066fbd9  /apex/com.android.runtime/lib/libart.so (artQuickToInterpreterBridge+1209) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #36 pc 0014503d  /apex/com.android.runtime/lib/libart.so (art_quick_to_interpreter_bridge+77) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #37 pc 0013e7d2  /apex/com.android.runtime/lib/libart.so (art_quick_invoke_stub+338) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #38 pc 00149a69  /apex/com.android.runtime/lib/libart.so (art::ArtMethod::Invoke(art::Thread*, unsigned int*, unsigned int, art::JValue*, char const*)+281) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #39 pc 0055a513  /apex/com.android.runtime/lib/libart.so (art::(anonymous namespace)::InvokeWithArgArray(art::ScopedObjectAccessAlreadyRunnable const&, art::ArtMethod*, art::(anonymous namespace)::ArgArray*, art::JValue*, char const*)+99) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #40 pc 0055bc7a  /apex/com.android.runtime/lib/libart.so (art::InvokeVirtualOrInterfaceWithVarArgs(art::ScopedObjectAccessAlreadyRunnable const&, _jobject*, _jmethodID*, char*)+474) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #41 pc 0040962f  /apex/com.android.runtime/lib/libart.so (art::JNI::CallVoidMethodV(_JNIEnv*, _jobject*, _jmethodID*, char*)+943) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #42 pc 003d8f44  /apex/com.android.runtime/lib/libart.so (art::(anonymous namespace)::CheckJNI::CallMethodV(char const*, _JNIEnv*, _jobject*, _jclass*, _jmethodID*, char*, art::Primitive::Type, art::InvokeType)+1700) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #43 pc 003c53f9  /apex/com.android.runtime/lib/libart.so (art::(anonymous namespace)::CheckJNI::CallVoidMethodV(_JNIEnv*, _jobject*, _jmethodID*, char*)+73) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #44 pc 000c6d5e  /system/lib/libandroid_runtime.so (_JNIEnv::CallVoidMethod(_jobject*, _jmethodID*, ...)+62) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #45 pc 0010df05  /system/lib/libandroid_runtime.so (_ZZN7androidL46android_view_RenderNode_requestPositionUpdatesEP7_JNIEnvP8_jobjectxS3_EN26PositionListenerTrampoline21doUpdatePositionAsyncExiiii+181) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #46 pc 0010e161  /system/lib/libandroid_runtime.so (_ZNSt3__110__function6__funcINS_6__bindINS_8__mem_fnIMZN7androidL46android_view_RenderNode_requestPositionUpdatesEP7_JNIEnvP8_jobjectxS8_E26PositionListenerTrampolineFvxiiiiEEEJPS9_xiiiiEEENS_9allocatorISE_EEFvvEEclEv+65) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #47 pc 0061c4a7  /system/lib/libhwui.so (std::__1::__packaged_task_func<std::__1::function<void ()>, std::__1::allocator<std::__1::allocator>, std::__1::function<void ()>>::operator()()+39) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #48 pc 002f47d0  /system/lib/libhwui.so (std::__1::packaged_task<void ()>::operator()()+96) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #49 pc 0038f3e3  /system/lib/libhwui.so (_ZNSt3__110__function6__funcIZN7android10uirenderer10CommonPool5asyncINS_8functionIFvvEEEEENS_6futureIDTclfp_EEEEOT_EUlvE_NS_9allocatorISE_EES7_EclEv+35) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #50 pc 00364252  /system/lib/libhwui.so (android::uirenderer::CommonPool::workerLoop()+210) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #51 pc 003640d0  /system/lib/libhwui.so (_ZNSt3__114__thread_proxyINS_5tupleIJNS_10unique_ptrINS_15__thread_structENS_14default_deleteIS3_EEEEZN7android10uirenderer10CommonPoolC1EvE3$_0EEEEEPvSC_+192) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #52 pc 0011a8e5  /apex/com.android.runtime/lib/bionic/libc.so (__pthread_start(void*)+53) (BuildId: 76290498408016ad14f4b98c3ab6c65c)
      #53 pc 000af6a7  /apex/com.android.runtime/lib/bionic/libc.so (__start_thread+71) (BuildId: 76290498408016ad14f4b98c3ab6c65c) 
```

```
*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***
Build fingerprint: 'google/sdk_gphone_x86/generic_x86:10/QSR1.190920.001/5891938:user/release-keys'
Revision: '0'
ABI: 'x86'
Timestamp: 2021-01-21 23:50:25-0500
pid: 7233, tid: 7295, name: hwuiTask1  >>> com.xspotlivin.mapboxcrash <<<
uid: 10139
signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0x4
Cause: null pointer dereference
    eax 00000000  ebx e73ec56c  ecx 00000001  edx bd28ce20
    edi 00000000  esi b6c0ca20
    ebp b6c0c938  esp b6c0c920  eip e73dea71
backtrace:
      #00 pc 0000fa71  /system/lib/libutils.so (android::RefBase::incStrong(void const*) const+33) (BuildId: 288ba3aff5b46dbd7e74be954af88b83)
      #01 pc 000c2571  /system/lib/libgui.so (android::TransactionCompletedListener::getInstance()+65) (BuildId: e21258ac2631be8934574fba6dbb8f64)
      #02 pc 000c3c1f  /system/lib/libgui.so (android::SurfaceComposerClient::doDropReferenceTransaction(android::sp<android::IBinder> const&, android::sp<android::ISurfaceComposerClient> const&)+1359) (BuildId: e21258ac2631be8934574fba6dbb8f64)
      #03 pc 000c042b  /system/lib/libgui.so (android::SurfaceControl::~SurfaceControl()+123) (BuildId: e21258ac2631be8934574fba6dbb8f64)
      #04 pc 000c05d1  /system/lib/libgui.so (android::SurfaceControl::~SurfaceControl()+33) (BuildId: e21258ac2631be8934574fba6dbb8f64)
      #05 pc 0000fb1f  /system/lib/libutils.so (android::RefBase::decStrong(void const*) const+95) (BuildId: 288ba3aff5b46dbd7e74be954af88b83)
      #06 pc 000c19f7  /system/lib/libgui.so (std::__1::pair<android::sp<android::ITransactionCompletedListener> const, android::SurfaceComposerClient::CallbackInfo>::~pair()+71) (BuildId: e21258ac2631be8934574fba6dbb8f64)
      #07 pc 000c514c  /system/lib/libgui.so (android::SurfaceComposerClient::Transaction::apply(bool)+460) (BuildId: e21258ac2631be8934574fba6dbb8f64)
      #08 pc 00111a9a  /system/lib/libandroid_runtime.so (android::nativeApplyTransaction(_JNIEnv*, _jclass*, long long, unsigned char)+42) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #09 pc 002a20ee  /system/framework/x86/boot-framework.oat (art_jni_trampoline+174) (BuildId: ff6ec03dd8445d20788424c92ba8ea28ad0f54f4)
      #10 pc 0013e9a2  /apex/com.android.runtime/lib/libart.so (art_quick_invoke_static_stub+418) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #11 pc 00149a7a  /apex/com.android.runtime/lib/libart.so (art::ArtMethod::Invoke(art::Thread*, unsigned int*, unsigned int, art::JValue*, char const*)+298) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #12 pc 00332502  /apex/com.android.runtime/lib/libart.so (art::interpreter::ArtInterpreterToCompiledCodeBridge(art::Thread*, art::ArtMethod*, art::ShadowFrame*, unsigned short, art::JValue*)+386) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #13 pc 0032c19c  /apex/com.android.runtime/lib/libart.so (bool art::interpreter::DoCall<false, false>(art::ArtMethod*, art::Thread*, art::ShadowFrame&, art::Instruction const*, unsigned short, art::JValue*)+988) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #14 pc 00684d03  /apex/com.android.runtime/lib/libart.so (MterpInvokeStatic+643) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #15 pc 001389a1  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_static+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #16 pc 00174de8  /system/framework/framework.jar (android.view.SurfaceControl.access$400)
      #17 pc 00684f6c  /apex/com.android.runtime/lib/libart.so (MterpInvokeStatic+1260) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #18 pc 001389a1  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_static+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #19 pc 00173f42  /system/framework/framework.jar (android.view.SurfaceControl$Transaction.apply+10)
      #20 pc 00681adc  /apex/com.android.runtime/lib/libart.so (MterpInvokeVirtual+1612) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #21 pc 00138821  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_virtual+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #22 pc 00173f1e  /system/framework/framework.jar (android.view.SurfaceControl$Transaction.apply+2)
      #23 pc 00681adc  /apex/com.android.runtime/lib/libart.so (MterpInvokeVirtual+1612) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #24 pc 00138821  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_virtual+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #25 pc 00176ab8  /system/framework/framework.jar (android.view.SurfaceView.setParentSpaceRectangle+36)
      #26 pc 006845ac  /apex/com.android.runtime/lib/libart.so (MterpInvokeDirect+1324) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #27 pc 00138921  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_direct+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #28 pc 00176560  /system/framework/framework.jar (android.view.SurfaceView.access$200)
      #29 pc 00684f6c  /apex/com.android.runtime/lib/libart.so (MterpInvokeStatic+1260) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #30 pc 001389a1  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_static+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #31 pc 00175d52  /system/framework/framework.jar (android.view.SurfaceView$3.positionChanged+142)
      #32 pc 006873b9  /apex/com.android.runtime/lib/libart.so (MterpInvokeInterfaceRange+1641) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #33 pc 00138d21  /apex/com.android.runtime/lib/libart.so (mterp_op_invoke_interface_range+33) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #34 pc 00380d40  /system/framework/framework.jar (android.graphics.RenderNode$CompositePositionUpdateListener.positionChanged+36)
      #35 pc 002f8e0a  /apex/com.android.runtime/lib/libart.so (_ZN3art11interpreterL7ExecuteEPNS_6ThreadERKNS_20CodeItemDataAccessorERNS_11ShadowFrameENS_6JValueEbb.llvm.12194892193087984976+298) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #36 pc 002ffcc5  /apex/com.android.runtime/lib/libart.so (art::interpreter::EnterInterpreterFromEntryPoint(art::Thread*, art::CodeItemDataAccessor const&, art::ShadowFrame*)+181) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #37 pc 0066fbd9  /apex/com.android.runtime/lib/libart.so (artQuickToInterpreterBridge+1209) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #38 pc 0014503d  /apex/com.android.runtime/lib/libart.so (art_quick_to_interpreter_bridge+77) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #39 pc 0013e7d2  /apex/com.android.runtime/lib/libart.so (art_quick_invoke_stub+338) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #40 pc 00149a69  /apex/com.android.runtime/lib/libart.so (art::ArtMethod::Invoke(art::Thread*, unsigned int*, unsigned int, art::JValue*, char const*)+281) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #41 pc 0055a513  /apex/com.android.runtime/lib/libart.so (art::(anonymous namespace)::InvokeWithArgArray(art::ScopedObjectAccessAlreadyRunnable const&, art::ArtMethod*, art::(anonymous namespace)::ArgArray*, art::JValue*, char const*)+99) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #42 pc 0055bc7a  /apex/com.android.runtime/lib/libart.so (art::InvokeVirtualOrInterfaceWithVarArgs(art::ScopedObjectAccessAlreadyRunnable const&, _jobject*, _jmethodID*, char*)+474) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #43 pc 0040962f  /apex/com.android.runtime/lib/libart.so (art::JNI::CallVoidMethodV(_JNIEnv*, _jobject*, _jmethodID*, char*)+943) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #44 pc 003d8f44  /apex/com.android.runtime/lib/libart.so (art::(anonymous namespace)::CheckJNI::CallMethodV(char const*, _JNIEnv*, _jobject*, _jclass*, _jmethodID*, char*, art::Primitive::Type, art::InvokeType)+1700) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #45 pc 003c53f9  /apex/com.android.runtime/lib/libart.so (art::(anonymous namespace)::CheckJNI::CallVoidMethodV(_JNIEnv*, _jobject*, _jmethodID*, char*)+73) (BuildId: 895645e5113da057f27d9b2ec11eb3bf)
      #46 pc 000c6d5e  /system/lib/libandroid_runtime.so (_JNIEnv::CallVoidMethod(_jobject*, _jmethodID*, ...)+62) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #47 pc 0010df05  /system/lib/libandroid_runtime.so (_ZZN7androidL46android_view_RenderNode_requestPositionUpdatesEP7_JNIEnvP8_jobjectxS3_EN26PositionListenerTrampoline21doUpdatePositionAsyncExiiii+181) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #48 pc 0010e161  /system/lib/libandroid_runtime.so (_ZNSt3__110__function6__funcINS_6__bindINS_8__mem_fnIMZN7androidL46android_view_RenderNode_requestPositionUpdatesEP7_JNIEnvP8_jobjectxS8_E26PositionListenerTrampolineFvxiiiiEEEJPS9_xiiiiEEENS_9allocatorISE_EEFvvEEclEv+65) (BuildId: 6ceb9761bceb97a18c92f8a4b7072247)
      #49 pc 0061c4a7  /system/lib/libhwui.so (std::__1::__packaged_task_func<std::__1::function<void ()>, std::__1::allocator<std::__1::allocator>, std::__1::function<void ()>>::operator()()+39) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #50 pc 002f47d0  /system/lib/libhwui.so (std::__1::packaged_task<void ()>::operator()()+96) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #51 pc 0038f3e3  /system/lib/libhwui.so (_ZNSt3__110__function6__funcIZN7android10uirenderer10CommonPool5asyncINS_8functionIFvvEEEEENS_6futureIDTclfp_EEEEOT_EUlvE_NS_9allocatorISE_EES7_EclEv+35) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #52 pc 00364252  /system/lib/libhwui.so (android::uirenderer::CommonPool::workerLoop()+210) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #53 pc 003640d0  /system/lib/libhwui.so (_ZNSt3__114__thread_proxyINS_5tupleIJNS_10unique_ptrINS_15__thread_structENS_14default_deleteIS3_EEEEZN7android10uirenderer10CommonPoolC1EvE3$_0EEEEEPvSC_+192) (BuildId: 57c0bd14db56e9b741ef49031022974d)
      #54 pc 0011a8e5  /apex/com.android.runtime/lib/bionic/libc.so (__pthread_start(void*)+53) (BuildId: 76290498408016ad14f4b98c3ab6c65c)
      #55 pc 000af6a7  /apex/com.android.runtime/lib/bionic/libc.so (__start_thread+71) (BuildId: 76290498408016ad14f4b98c3ab6c65c)
```
