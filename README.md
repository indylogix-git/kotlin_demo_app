# kotlin_demo_app

<p>As lot of people and freshers want to learn Kotlin, use this sample Kotlin application to learn basics of Kotlin to design Login, Register and Dashboard page.</p>

<p>The code contains activities and fragments both; Left drawer menu, Top tab menus with slide view pager. </p>

<p>Major inclusions in code:</p>
<p>Profile view</p>
<p>Share preference</p>
<p>Calendra picker Dialog</p>
<p>Time Picker Dialog</p>
<p>Calling with Access Permission</p>
<p>Tab Bar with View Pagar</p>

If you want to outsource your Kotlin project, email us on info@indylogix.com


## Installation

Follow the install instructions [here](https://pub.dev/packages/kotlin_login#-installing-tab-)

## Reference

Property |   Type     | Desciption
-------- |------------| ---------------
onSignup |   `AuthCallback`     | <sub>Called when the user hit the submit button when in sign up mode</sub>
onLogin |   `AuthCallback`     | <sub>Called when the user hit the submit button when in login mode</sub>
onRecoverPassword |   `RecoverCallback`     | <sub>Called when the user hit the submit button when in recover password mode</sub>
title |   `String`     | <sub>The large text above the login [Card], usually the app or company name. Leave the string empty or null if you want no title.</sub>
logo |   `String`     | <sub>The path to the asset image that will be passed to the `Image.asset()`</sub>
messages |   [`LoginMessages`](#LoginMessages)     | <sub>Describes all of the labels, text hints, button texts and other auth descriptions</sub>
theme |   [`LoginTheme`](#LoginTheme)     | <sub>KotlinLogin's theme. If not specified, it will use the default theme as shown in the demo gifs and use the colorsheme in the closest `Theme` widget</sub>
emailValidator |   <sub>`FormFieldValidator<String>`</sub>     | <sub>Email validating logic, Returns an error string to display if the input is invalid, or null otherwise</sub>
passwordValidator | <sub>`FormFieldValidator<String>`</sub>     | <sub>Same as `emailValidator` but for password</sub>
<sub>onSubmitAnimationCompleted</sub> |   `Function`     | <sub>Called after the submit animation's completed. Put your route transition logic here</sub>
logoTag |   `String`     | <sub>`Hero` tag for logo image. If not specified, it will simply fade out when changing route</sub>
titleTag |   `String`     | <sub>`Hero` tag for title text. Need to specify `LoginTheme.beforeHeroFontSize` and `LoginTheme.afterHeroFontSize` if you want different font size before and after hero animation</sub>
showDebugButtons |   `bool`     | <sub>Display the debug buttons to quickly forward/reverse login animations. In release mode, this will be overrided to `false` regardless of the value passed in</sub>
