## 📲 How to Install

### Android Studio

Add this line to your app's `build.gradle` inside the `dependencies` section:

   ```android
   implementation 'com.mercadolibre.android:carddrawer:1.+'
   ```
Add this line to your project's `build.gradle` inside the `repositories` section:   
   
   ```android
    maven {
            url "https://dl.bintray.com/mercadolibre/android-public"
        }
   ```   
   
## 🌟 Features
- [x] Easy to integrate
- [x] Card number, name, expiration date and CVV support (Card)
- [x] Card left and right image customization (CardUI)
- [x] CVV support at front & back view
- [x] Card flip animation integrated
- [x] Live card view updated while CardData protocol is edited
- [x] Support for custom overlay background image

## 💡 Advanced features
### Show security code

![Gif](https://i.imgur.com/H2psku8.gif)
```java
// You can highlight the security code location. 
// If the security code is behind, the card will transition with flip animation.
cardDrawerView.showSecurityCode();
```
### Show front card view
```java
cardDrawerView.show();
```
## 💳 Card data structure and style customization
You can customize the data structure and style of your card.

### 🔠 Card
Using `Card` to update the card display values.
```java
public class Card extends Observable implements Parcelable {
    private String number;
    private String name;
    private String expiration;
    private String secCode;
...

```

### 🎨 CardUI 
Using `CardUI` to customize: position of security code, card background, font color, place holders, etc.
```java
public interface CardUI {
    /**
     * @return the bank logo to show
     */
    @DrawableRes
    int getBankImageRes();

    /**
     * @return the card logo to show
     */
    @DrawableRes
    int getCardLogoImageRes();

    /**
     * Sets the card logo image to the Imageview
     * @param cardLogo
     */
    void setCardLogoImage(@NonNull ImageView cardLogo);

    /**
     * Sets bank image to the Imageview
     * @param bankImage
     */
    void setBankImage(@NonNull ImageView bankImage);

    /**
     * @return the security code position
     */
    @SecurityCodeLocation
    String getSecurityCodeLocation();

    /**
     * @return color for text
     */
    @ColorInt
    int getCardFontColor();

    /**
     * @return color for paint de card
     */
    @ColorInt
    int getCardBackgroundColor();

    /**
     * @return number long
     */
    int getSecurityCodePattern();

    /**
     * Ej: **** **** **** ****
     *
     * @return the group of numbers
     */
    int[] getCardNumberPattern();

    /**
     * Ej: NOMBRE Y APELLIDO
     *
     * @return the name place holder to show
     */
    String getNamePlaceHolder();

    /**
     * Ej: MM/AA
     *
     * @return the expiration date place holder to show
     */
    String getExpirationPlaceHolder();

    /**
     * @return the font type
     */
    @FontType
    String getFontType();

    /**
     * @return the animation type
     */
    @CardAnimationType
    String getAnimationType();
}
```

## How to make a local publish
```
./gradlew build publishToMavenLocal
```
Pro tip: first change the version name in ```gradle.properties``` to ```X.XX.X-LOCAL```

## 👨🏻‍💻 Author
Mercado Libre

## 👮🏻 License

```
Copyright 2019 Mercadolibre Developers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
