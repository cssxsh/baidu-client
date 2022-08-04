package xyz.cssxsh.baidu.aip.tts

/**
 * [demo](https://ai.baidu.com/tech/speech/tts_online)
 */
public object SpeechPerson {
    /**
     * 基础音库
     */
    public object Base {
        /**
         * 度小美-成熟女声
         */
        public const val MatureFemale: Int = 0

        /**
         * 度小宇-成熟男声
         */
        public const val MatureMale: Int = 1

        /**
         * 度逍遥-磁性男声
         */
        public const val MagneticMale: Int = 3

        /**
         * 度丫丫-可爱女童
         */
        public const val LovelyGirl: Int = 4
    }

    /**
     * 精品音库
     */
    public object Boutique {
        /**
         * 度小娇-情感女声
         */
        public const val EmotionalFemale: Int = 5

        /**
         * 度米朵-可爱女童
         */
        public const val LovelyGirl: Int = 103

        /**
         * 度博文-情感男声
         */
        public const val EmotionalMale: Int = 106

        /**
         * 度小萌-可爱女声
         */
        public const val LovelyFemale: Int = 111

        /**
         * 度小童-活泼女童
         */
        public const val LivelyGirl: Int = 110

        /**
         * 度逍遥-磁性男声
         */
        public const val MagneticMale: Int = 5003

        /**
         * 度小鹿-甜美女声
         */
        public const val SweetFemale: Int = 5118
    }

    /**
     * 臻品音库
     */
    public object Top {

        /**
         * 度逍遥-磁性男声
         */
        public const val MagneticMale: Int = 4003

        /**
         * 度小雯-成熟女声
         */
        public const val MatureFemale: Int = 4100

        /**
         * 度米朵-可爱女童
         */
        public const val LovelyGirl: Int = 4103

        /**
         * 度灵儿-清澈女声
         */
        public const val ClearFemale: Int = 4105

        /**
         * 度博文-情感男声
         */
        public const val EmotionalMale: Int = 4106

        /**
         * 度小贤-成熟男声
         */
        public const val MatureMale: Int = 4115

        /**
         * 度小乔-情感女声
         */
        public const val EmotionalFemale: Int = 4117

        /**
         * 度小鹿-甜美女声
         */
        public const val SweetFemale: Int = 4119

    }
}