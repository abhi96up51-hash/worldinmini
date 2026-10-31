package com.example.data

import com.example.model.Continent
import com.example.model.SubTimeZoneInfo
import com.example.model.WorldCountry
import java.util.Locale
import java.util.TimeZone

object WorldCountriesCatalog {

    val allCountries: List<WorldCountry> by lazy {
        buildCompleteWorldCatalog()
    }

    private fun buildCompleteWorldCatalog(): List<WorldCountry> {
        val curated = listOf(
            // ================= ASIA =================
            WorldCountry("AF", "Afghanistan", "AF", "Kabul", Continent.ASIA, "Asia/Kabul", "+93", "AFN"),
            WorldCountry("AM", "Armenia", "AM", "Yerevan", Continent.ASIA, "Asia/Yerevan", "+374", "AMD"),
            WorldCountry("AZ", "Azerbaijan", "AZ", "Baku", Continent.ASIA, "Asia/Baku", "+994", "AZN"),
            WorldCountry("BH", "Bahrain", "BH", "Manama", Continent.ASIA, "Asia/Bahrain", "+973", "BHD"),
            WorldCountry("BD", "Bangladesh", "BD", "Dhaka", Continent.ASIA, "Asia/Dhaka", "+880", "BDT"),
            WorldCountry("BT", "Bhutan", "BT", "Thimphu", Continent.ASIA, "Asia/Thimphu", "+975", "BTN"),
            WorldCountry("BN", "Brunei", "BN", "Bandar Seri Begawan", Continent.ASIA, "Asia/Brunei", "+673", "BND"),
            WorldCountry("KH", "Cambodia", "KH", "Phnom Penh", Continent.ASIA, "Asia/Phnom_Penh", "+855", "KHR"),
            WorldCountry(
                "CN", "China", "CN", "Beijing / Shanghai", Continent.ASIA, "Asia/Shanghai", "+86", "CNY",
                listOf(
                    SubTimeZoneInfo("Beijing / Shanghai (CST)", "Asia/Shanghai"),
                    SubTimeZoneInfo("Urumqi (Xinjiang)", "Asia/Urumqi")
                )
            ),
            WorldCountry("CY", "Cyprus", "CY", "Nicosia", Continent.ASIA, "Asia/Nicosia", "+357", "EUR"),
            WorldCountry("GE", "Georgia", "GE", "Tbilisi", Continent.ASIA, "Asia/Tbilisi", "+995", "GEL"),
            WorldCountry("HK", "Hong Kong", "HK", "Hong Kong", Continent.ASIA, "Asia/Hong_Kong", "+852", "HKD"),
            WorldCountry("IN", "India", "IN", "New Delhi / Mumbai", Continent.ASIA, "Asia/Kolkata", "+91", "INR"),
            WorldCountry(
                "ID", "Indonesia", "ID", "Jakarta", Continent.ASIA, "Asia/Jakarta", "+62", "IDR",
                listOf(
                    SubTimeZoneInfo("Jakarta / Sumatra (WIB)", "Asia/Jakarta"),
                    SubTimeZoneInfo("Bali / Makassar (WITA)", "Asia/Makassar"),
                    SubTimeZoneInfo("Jayapura / Papua (WIT)", "Asia/Jayapura")
                )
            ),
            WorldCountry("IR", "Iran", "IR", "Tehran", Continent.ASIA, "Asia/Tehran", "+98", "IRR"),
            WorldCountry("IQ", "Iraq", "IQ", "Baghdad", Continent.ASIA, "Asia/Baghdad", "+964", "IQD"),
            WorldCountry("IL", "Israel", "IL", "Jerusalem / Tel Aviv", Continent.ASIA, "Asia/Jerusalem", "+972", "ILS"),
            WorldCountry("JP", "Japan", "JP", "Tokyo", Continent.ASIA, "Asia/Tokyo", "+81", "JPY"),
            WorldCountry("JO", "Jordan", "JO", "Amman", Continent.ASIA, "Asia/Amman", "+962", "JOD"),
            WorldCountry(
                "KZ", "Kazakhstan", "KZ", "Astana / Almaty", Continent.ASIA, "Asia/Almaty", "+7", "KZT",
                listOf(
                    SubTimeZoneInfo("Astana / Almaty", "Asia/Almaty"),
                    SubTimeZoneInfo("Aqtobe / West Kazakhstan", "Asia/Aqtobe")
                )
            ),
            WorldCountry("KW", "Kuwait", "KW", "Kuwait City", Continent.ASIA, "Asia/Kuwait", "+965", "KWD"),
            WorldCountry("KG", "Kyrgyzstan", "KG", "Bishkek", Continent.ASIA, "Asia/Bishkek", "+996", "KGS"),
            WorldCountry("LA", "Laos", "LA", "Vientiane", Continent.ASIA, "Asia/Vientiane", "+856", "LAK"),
            WorldCountry("LB", "Lebanon", "LB", "Beirut", Continent.ASIA, "Asia/Beirut", "+961", "LBP"),
            WorldCountry("MO", "Macau", "MO", "Macau", Continent.ASIA, "Asia/Macau", "+853", "MOP"),
            WorldCountry("MY", "Malaysia", "MY", "Kuala Lumpur", Continent.ASIA, "Asia/Kuala_Lumpur", "+60", "MYR"),
            WorldCountry("MV", "Maldives", "MV", "Malé", Continent.ASIA, "Indian/Maldives", "+960", "MVR"),
            WorldCountry(
                "MN", "Mongolia", "MN", "Ulaanbaatar", Continent.ASIA, "Asia/Ulaanbaatar", "+976", "MNT",
                listOf(
                    SubTimeZoneInfo("Ulaanbaatar (Central/East)", "Asia/Ulaanbaatar"),
                    SubTimeZoneInfo("Hovd (Western)", "Asia/Hovd")
                )
            ),
            WorldCountry("MM", "Myanmar", "MM", "Naypyidaw / Yangon", Continent.ASIA, "Asia/Yangon", "+95", "MMK"),
            WorldCountry("NP", "Nepal", "NP", "Kathmandu", Continent.ASIA, "Asia/Kathmandu", "+977", "NPR"),
            WorldCountry("KP", "North Korea", "KP", "Pyongyang", Continent.ASIA, "Asia/Pyongyang", "+850", "KPW"),
            WorldCountry("OM", "Oman", "OM", "Muscat", Continent.ASIA, "Asia/Muscat", "+968", "OMR"),
            WorldCountry("PK", "Pakistan", "PK", "Islamabad / Karachi", Continent.ASIA, "Asia/Karachi", "+92", "PKR"),
            WorldCountry("PS", "Palestine", "PS", "Ramallah / Gaza", Continent.ASIA, "Asia/Hebron", "+970", "ILS"),
            WorldCountry("PH", "Philippines", "PH", "Manila", Continent.ASIA, "Asia/Manila", "+63", "PHP"),
            WorldCountry("QA", "Qatar", "QA", "Doha", Continent.ASIA, "Asia/Qatar", "+974", "QAR"),
            WorldCountry("SA", "Saudi Arabia", "SA", "Riyadh", Continent.ASIA, "Asia/Riyadh", "+966", "SAR"),
            WorldCountry("SG", "Singapore", "SG", "Singapore", Continent.ASIA, "Asia/Singapore", "+65", "SGD"),
            WorldCountry("KR", "South Korea", "KR", "Seoul", Continent.ASIA, "Asia/Seoul", "+82", "KRW"),
            WorldCountry("LK", "Sri Lanka", "LK", "Colombo", Continent.ASIA, "Asia/Colombo", "+94", "LKR"),
            WorldCountry("SY", "Syria", "SY", "Damascus", Continent.ASIA, "Asia/Damascus", "+963", "SYP"),
            WorldCountry("TW", "Taiwan", "TW", "Taipei", Continent.ASIA, "Asia/Taipei", "+886", "TWD"),
            WorldCountry("TJ", "Tajikistan", "TJ", "Dushanbe", Continent.ASIA, "Asia/Dushanbe", "+992", "TJS"),
            WorldCountry("TH", "Thailand", "TH", "Bangkok", Continent.ASIA, "Asia/Bangkok", "+66", "THB"),
            WorldCountry("TL", "Timor-Leste", "TL", "Dili", Continent.ASIA, "Asia/Dili", "+670", "USD"),
            WorldCountry("TR", "Turkey", "TR", "Ankara / Istanbul", Continent.ASIA, "Europe/Istanbul", "+90", "TRY"),
            WorldCountry("TM", "Turkmenistan", "TM", "Ashgabat", Continent.ASIA, "Asia/Ashgabat", "+993", "TMT"),
            WorldCountry("AE", "United Arab Emirates", "AE", "Abu Dhabi / Dubai", Continent.ASIA, "Asia/Dubai", "+971", "AED"),
            WorldCountry("UZ", "Uzbekistan", "UZ", "Tashkent", Continent.ASIA, "Asia/Tashkent", "+998", "UZS"),
            WorldCountry("VN", "Vietnam", "VN", "Hanoi / Ho Chi Minh", Continent.ASIA, "Asia/Ho_Chi_Minh", "+84", "VND"),
            WorldCountry("YE", "Yemen", "YE", "Sana'a", Continent.ASIA, "Asia/Aden", "+967", "YER"),

            // ================= EUROPE =================
            WorldCountry("AL", "Albania", "AL", "Tirana", Continent.EUROPE, "Europe/Tirane", "+355", "ALL"),
            WorldCountry("AD", "Andorra", "AD", "Andorra la Vella", Continent.EUROPE, "Europe/Andorra", "+376", "EUR"),
            WorldCountry("AT", "Austria", "AT", "Vienna", Continent.EUROPE, "Europe/Vienna", "+43", "EUR"),
            WorldCountry("BY", "Belarus", "BY", "Minsk", Continent.EUROPE, "Europe/Minsk", "+375", "BYN"),
            WorldCountry("BE", "Belgium", "BE", "Brussels", Continent.EUROPE, "Europe/Brussels", "+32", "EUR"),
            WorldCountry("BA", "Bosnia and Herzegovina", "BA", "Sarajevo", Continent.EUROPE, "Europe/Sarajevo", "+387", "BAM"),
            WorldCountry("BG", "Bulgaria", "BG", "Sofia", Continent.EUROPE, "Europe/Sofia", "+359", "BGN"),
            WorldCountry("HR", "Croatia", "HR", "Zagreb", Continent.EUROPE, "Europe/Zagreb", "+385", "EUR"),
            WorldCountry("CZ", "Czechia", "CZ", "Prague", Continent.EUROPE, "Europe/Prague", "+420", "CZK"),
            WorldCountry("DK", "Denmark", "DK", "Copenhagen", Continent.EUROPE, "Europe/Copenhagen", "+45", "DKK"),
            WorldCountry("EE", "Estonia", "EE", "Tallinn", Continent.EUROPE, "Europe/Tallinn", "+372", "EUR"),
            WorldCountry("FO", "Faroe Islands", "FO", "Tórshavn", Continent.EUROPE, "Atlantic/Faroe", "+298", "DKK"),
            WorldCountry("FI", "Finland", "FI", "Helsinki", Continent.EUROPE, "Europe/Helsinki", "+358", "EUR"),
            WorldCountry("FR", "France", "FR", "Paris", Continent.EUROPE, "Europe/Paris", "+33", "EUR"),
            WorldCountry("DE", "Germany", "DE", "Berlin / Frankfurt", Continent.EUROPE, "Europe/Berlin", "+49", "EUR"),
            WorldCountry("GI", "Gibraltar", "GI", "Gibraltar", Continent.EUROPE, "Europe/Gibraltar", "+350", "GIP"),
            WorldCountry("GR", "Greece", "GR", "Athens", Continent.EUROPE, "Europe/Athens", "+30", "EUR"),
            WorldCountry("GG", "Guernsey", "GG", "St. Peter Port", Continent.EUROPE, "Europe/Guernsey", "+44", "GBP"),
            WorldCountry("HU", "Hungary", "HU", "Budapest", Continent.EUROPE, "Europe/Budapest", "+36", "HUF"),
            WorldCountry("IS", "Iceland", "IS", "Reykjavik", Continent.EUROPE, "Atlantic/Reykjavik", "+354", "ISK"),
            WorldCountry("IE", "Ireland", "IE", "Dublin", Continent.EUROPE, "Europe/Dublin", "+353", "EUR"),
            WorldCountry("IM", "Isle of Man", "IM", "Douglas", Continent.EUROPE, "Europe/Isle_of_Man", "+44", "GBP"),
            WorldCountry("IT", "Italy", "IT", "Rome / Milan", Continent.EUROPE, "Europe/Rome", "+39", "EUR"),
            WorldCountry("JE", "Jersey", "JE", "Saint Helier", Continent.EUROPE, "Europe/Jersey", "+44", "GBP"),
            WorldCountry("XK", "Kosovo", "XK", "Pristina", Continent.EUROPE, "Europe/Belgrade", "+383", "EUR"),
            WorldCountry("LV", "Latvia", "LV", "Riga", Continent.EUROPE, "Europe/Riga", "+371", "EUR"),
            WorldCountry("LI", "Liechtenstein", "LI", "Vaduz", Continent.EUROPE, "Europe/Vaduz", "+423", "CHF"),
            WorldCountry("LT", "Lithuania", "LT", "Vilnius", Continent.EUROPE, "Europe/Vilnius", "+370", "EUR"),
            WorldCountry("LU", "Luxembourg", "LU", "Luxembourg", Continent.EUROPE, "Europe/Luxembourg", "+352", "EUR"),
            WorldCountry("MT", "Malta", "MT", "Valletta", Continent.EUROPE, "Europe/Malta", "+356", "EUR"),
            WorldCountry("MD", "Moldova", "MD", "Chișinău", Continent.EUROPE, "Europe/Chisinau", "+373", "MDL"),
            WorldCountry("MC", "Monaco", "MC", "Monaco", Continent.EUROPE, "Europe/Monaco", "+377", "EUR"),
            WorldCountry("ME", "Montenegro", "ME", "Podgorica", Continent.EUROPE, "Europe/Podgorica", "+382", "EUR"),
            WorldCountry("NL", "Netherlands", "NL", "Amsterdam", Continent.EUROPE, "Europe/Amsterdam", "+31", "EUR"),
            WorldCountry("MK", "North Macedonia", "MK", "Skopje", Continent.EUROPE, "Europe/Skopje", "+389", "MKD"),
            WorldCountry("NO", "Norway", "NO", "Oslo", Continent.EUROPE, "Europe/Oslo", "+47", "NOK"),
            WorldCountry("PL", "Poland", "PL", "Warsaw", Continent.EUROPE, "Europe/Warsaw", "+48", "PLN"),
            WorldCountry(
                "PT", "Portugal", "PT", "Lisbon", Continent.EUROPE, "Europe/Lisbon", "+351", "EUR",
                listOf(
                    SubTimeZoneInfo("Lisbon / Mainland (WET)", "Europe/Lisbon"),
                    SubTimeZoneInfo("Azores Islands", "Atlantic/Azores")
                )
            ),
            WorldCountry("RO", "Romania", "RO", "Bucharest", Continent.EUROPE, "Europe/Bucharest", "+40", "RON"),
            WorldCountry(
                "RU", "Russia", "RU", "Moscow", Continent.EUROPE, "Europe/Moscow", "+7", "RUB",
                listOf(
                    SubTimeZoneInfo("Kaliningrad (UTC+2)", "Europe/Kaliningrad"),
                    SubTimeZoneInfo("Moscow / St. Petersburg (MSK)", "Europe/Moscow"),
                    SubTimeZoneInfo("Samara (UTC+4)", "Europe/Samara"),
                    SubTimeZoneInfo("Yekaterinburg (UTC+5)", "Asia/Yekaterinburg"),
                    SubTimeZoneInfo("Omsk (UTC+6)", "Asia/Omsk"),
                    SubTimeZoneInfo("Novosibirsk / Krasnoyarsk (UTC+7)", "Asia/Krasnoyarsk"),
                    SubTimeZoneInfo("Irkutsk (UTC+8)", "Asia/Irkutsk"),
                    SubTimeZoneInfo("Yakutsk (UTC+9)", "Asia/Yakutsk"),
                    SubTimeZoneInfo("Vladivostok (UTC+10)", "Asia/Vladivostok"),
                    SubTimeZoneInfo("Magadan / Sakhalin (UTC+11)", "Asia/Magadan"),
                    SubTimeZoneInfo("Kamchatka (UTC+12)", "Asia/Kamchatka")
                )
            ),
            WorldCountry("SM", "San Marino", "SM", "San Marino", Continent.EUROPE, "Europe/San_Marino", "+378", "EUR"),
            WorldCountry("RS", "Serbia", "RS", "Belgrade", Continent.EUROPE, "Europe/Belgrade", "+381", "RSD"),
            WorldCountry("SK", "Slovakia", "SK", "Bratislava", Continent.EUROPE, "Europe/Bratislava", "+421", "EUR"),
            WorldCountry("SI", "Slovenia", "SI", "Ljubljana", Continent.EUROPE, "Europe/Ljubljana", "+386", "EUR"),
            WorldCountry(
                "ES", "Spain", "ES", "Madrid / Barcelona", Continent.EUROPE, "Europe/Madrid", "+34", "EUR",
                listOf(
                    SubTimeZoneInfo("Madrid / Mainland (CET)", "Europe/Madrid"),
                    SubTimeZoneInfo("Canary Islands (WET)", "Atlantic/Canary")
                )
            ),
            WorldCountry("SJ", "Svalbard and Jan Mayen", "SJ", "Longyearbyen", Continent.EUROPE, "Arctic/Longyearbyen", "+47", "NOK"),
            WorldCountry("SE", "Sweden", "SE", "Stockholm", Continent.EUROPE, "Europe/Stockholm", "+46", "SEK"),
            WorldCountry("CH", "Switzerland", "CH", "Bern / Zurich / Geneva", Continent.EUROPE, "Europe/Zurich", "+41", "CHF"),
            WorldCountry("UA", "Ukraine", "UA", "Kyiv", Continent.EUROPE, "Europe/Kyiv", "+380", "UAH"),
            WorldCountry("GB", "United Kingdom", "GB", "London", Continent.EUROPE, "Europe/London", "+44", "GBP"),
            WorldCountry("VA", "Vatican City", "VA", "Vatican City", Continent.EUROPE, "Europe/Vatican", "+379", "EUR"),

            // ================= AFRICA =================
            WorldCountry("DZ", "Algeria", "DZ", "Algiers", Continent.AFRICA, "Africa/Algiers", "+213", "DZD"),
            WorldCountry("AO", "Angola", "AO", "Luanda", Continent.AFRICA, "Africa/Luanda", "+244", "AOA"),
            WorldCountry("BJ", "Benin", "BJ", "Porto-Novo / Cotonou", Continent.AFRICA, "Africa/Porto-Novo", "+229", "XOF"),
            WorldCountry("BW", "Botswana", "BW", "Gaborone", Continent.AFRICA, "Africa/Gaborone", "+267", "BWP"),
            WorldCountry("BF", "Burkina Faso", "BF", "Ouagadougou", Continent.AFRICA, "Africa/Ouagadougou", "+226", "XOF"),
            WorldCountry("BI", "Burundi", "BI", "Gitega / Bujumbura", Continent.AFRICA, "Africa/Bujumbura", "+257", "BIF"),
            WorldCountry("CV", "Cabo Verde", "CV", "Praia", Continent.AFRICA, "Atlantic/Cape_Verde", "+238", "CVE"),
            WorldCountry("CM", "Cameroon", "CM", "Yaoundé / Douala", Continent.AFRICA, "Africa/Douala", "+237", "XAF"),
            WorldCountry("CF", "Central African Republic", "CF", "Bangui", Continent.AFRICA, "Africa/Bangui", "+236", "XAF"),
            WorldCountry("TD", "Chad", "TD", "N'Djamena", Continent.AFRICA, "Africa/Ndjamena", "+235", "XAF"),
            WorldCountry("KM", "Comoros", "KM", "Moroni", Continent.AFRICA, "Indian/Comoro", "+269", "KMF"),
            WorldCountry("CG", "Congo (Republic)", "CG", "Brazzaville", Continent.AFRICA, "Africa/Brazzaville", "+242", "XAF"),
            WorldCountry(
                "CD", "Congo (DRC)", "CD", "Kinshasa", Continent.AFRICA, "Africa/Kinshasa", "+243", "CDF",
                listOf(
                    SubTimeZoneInfo("Kinshasa (West - WAT)", "Africa/Kinshasa"),
                    SubTimeZoneInfo("Lubumbashi (East - CAT)", "Africa/Lubumbashi")
                )
            ),
            WorldCountry("CI", "Côte d'Ivoire", "CI", "Abidjan / Yamoussoukro", Continent.AFRICA, "Africa/Abidjan", "+225", "XOF"),
            WorldCountry("DJ", "Djibouti", "DJ", "Djibouti", Continent.AFRICA, "Africa/Djibouti", "+253", "DJF"),
            WorldCountry("EG", "Egypt", "EG", "Cairo", Continent.AFRICA, "Africa/Cairo", "+20", "EGP"),
            WorldCountry("GQ", "Equatorial Guinea", "GQ", "Malabo", Continent.AFRICA, "Africa/Malabo", "+240", "XAF"),
            WorldCountry("ER", "Eritrea", "ER", "Asmara", Continent.AFRICA, "Africa/Asmara", "+291", "ERN"),
            WorldCountry("SZ", "Eswatini", "SZ", "Mbabane", Continent.AFRICA, "Africa/Mbabane", "+268", "SZL"),
            WorldCountry("ET", "Ethiopia", "ET", "Addis Ababa", Continent.AFRICA, "Africa/Addis_Ababa", "+251", "ETB"),
            WorldCountry("GA", "Gabon", "GA", "Libreville", Continent.AFRICA, "Africa/Libreville", "+241", "XAF"),
            WorldCountry("GM", "Gambia", "GM", "Banjul", Continent.AFRICA, "Africa/Banjul", "+220", "GMD"),
            WorldCountry("GH", "Ghana", "GH", "Accra", Continent.AFRICA, "Africa/Accra", "+233", "GHS"),
            WorldCountry("GN", "Guinea", "GN", "Conakry", Continent.AFRICA, "Africa/Conakry", "+224", "GNF"),
            WorldCountry("GW", "Guinea-Bissau", "GW", "Bissau", Continent.AFRICA, "Africa/Bissau", "+245", "XOF"),
            WorldCountry("KE", "Kenya", "KE", "Nairobi", Continent.AFRICA, "Africa/Nairobi", "+254", "KES"),
            WorldCountry("LS", "Lesotho", "LS", "Maseru", Continent.AFRICA, "Africa/Maseru", "+266", "LSL"),
            WorldCountry("LR", "Liberia", "LR", "Monrovia", Continent.AFRICA, "Africa/Monrovia", "+231", "LRD"),
            WorldCountry("LY", "Libya", "LY", "Tripoli", Continent.AFRICA, "Africa/Tripoli", "+218", "LYD"),
            WorldCountry("MG", "Madagascar", "MG", "Antananarivo", Continent.AFRICA, "Indian/Antananarivo", "+261", "MGA"),
            WorldCountry("MW", "Malawi", "MW", "Lilongwe", Continent.AFRICA, "Africa/Blantyre", "+265", "MWK"),
            WorldCountry("ML", "Mali", "ML", "Bamako", Continent.AFRICA, "Africa/Bamako", "+223", "XOF"),
            WorldCountry("MR", "Mauritania", "MR", "Nouakchott", Continent.AFRICA, "Africa/Nouakchott", "+222", "MRU"),
            WorldCountry("MU", "Mauritius", "MU", "Port Louis", Continent.AFRICA, "Indian/Mauritius", "+230", "MUR"),
            WorldCountry("YT", "Mayotte", "YT", "Mamoudzou", Continent.AFRICA, "Indian/Mayotte", "+262", "EUR"),
            WorldCountry("MA", "Morocco", "MA", "Rabat / Casablanca", Continent.AFRICA, "Africa/Casablanca", "+212", "MAD"),
            WorldCountry("MZ", "Mozambique", "MZ", "Maputo", Continent.AFRICA, "Africa/Maputo", "+258", "MZN"),
            WorldCountry("NA", "Namibia", "NA", "Windhoek", Continent.AFRICA, "Africa/Windhoek", "+264", "NAD"),
            WorldCountry("NE", "Niger", "NE", "Niamey", Continent.AFRICA, "Africa/Niamey", "+227", "XOF"),
            WorldCountry("NG", "Nigeria", "NG", "Abuja / Lagos", Continent.AFRICA, "Africa/Lagos", "+234", "NGN"),
            WorldCountry("RE", "Réunion", "RE", "Saint-Denis", Continent.AFRICA, "Indian/Reunion", "+262", "EUR"),
            WorldCountry("RW", "Rwanda", "RW", "Kigali", Continent.AFRICA, "Africa/Kigali", "+250", "RWF"),
            WorldCountry("SH", "Saint Helena", "SH", "Jamestown", Continent.AFRICA, "Atlantic/St_Helena", "+290", "SHP"),
            WorldCountry("ST", "São Tomé and Príncipe", "ST", "São Tomé", Continent.AFRICA, "Africa/Sao_Tome", "+239", "STN"),
            WorldCountry("SN", "Senegal", "SN", "Dakar", Continent.AFRICA, "Africa/Dakar", "+221", "XOF"),
            WorldCountry("SC", "Seychelles", "SC", "Victoria", Continent.AFRICA, "Indian/Mahe", "+248", "SCR"),
            WorldCountry("SL", "Sierra Leone", "SL", "Freetown", Continent.AFRICA, "Africa/Freetown", "+232", "SLE"),
            WorldCountry("SO", "Somalia", "SO", "Mogadishu", Continent.AFRICA, "Africa/Mogadishu", "+252", "SOS"),
            WorldCountry("ZA", "South Africa", "ZA", "Pretoria / Johannesburg", Continent.AFRICA, "Africa/Johannesburg", "+27", "ZAR"),
            WorldCountry("SS", "South Sudan", "SS", "Juba", Continent.AFRICA, "Africa/Juba", "+211", "SSP"),
            WorldCountry("SD", "Sudan", "SD", "Khartoum", Continent.AFRICA, "Africa/Khartoum", "+249", "SDG"),
            WorldCountry("TZ", "Tanzania", "TZ", "Dodoma / Dar es Salaam", Continent.AFRICA, "Africa/Dar_es_Salaam", "+255", "TZS"),
            WorldCountry("TG", "Togo", "TG", "Lomé", Continent.AFRICA, "Africa/Lome", "+228", "XOF"),
            WorldCountry("TN", "Tunisia", "TN", "Tunis", Continent.AFRICA, "Africa/Tunis", "+216", "TND"),
            WorldCountry("UG", "Uganda", "UG", "Kampala", Continent.AFRICA, "Africa/Kampala", "+256", "UGX"),
            WorldCountry("EH", "Western Sahara", "EH", "El Aaiún", Continent.AFRICA, "Africa/El_Aaiun", "+212", "MAD"),
            WorldCountry("ZM", "Zambia", "ZM", "Lusaka", Continent.AFRICA, "Africa/Lusaka", "+260", "ZMW"),
            WorldCountry("ZW", "Zimbabwe", "ZW", "Harare", Continent.AFRICA, "Africa/Harare", "+263", "ZWL"),

            // ================= AMERICAS =================
            WorldCountry("AI", "Anguilla", "AI", "The Valley", Continent.AMERICAS, "America/Anguilla", "+1-264", "XCD"),
            WorldCountry("AG", "Antigua and Barbuda", "AG", "St. John's", Continent.AMERICAS, "America/Antigua", "+1-268", "XCD"),
            WorldCountry("AR", "Argentina", "AR", "Buenos Aires", Continent.AMERICAS, "America/Argentina/Buenos_Aires", "+54", "ARS"),
            WorldCountry("AW", "Aruba", "AW", "Oranjestad", Continent.AMERICAS, "America/Aruba", "+297", "AWG"),
            WorldCountry("BS", "Bahamas", "BS", "Nassau", Continent.AMERICAS, "America/Nassau", "+1-242", "BSD"),
            WorldCountry("BB", "Barbados", "BB", "Bridgetown", Continent.AMERICAS, "America/Barbados", "+1-246", "BBD"),
            WorldCountry("BZ", "Belize", "BZ", "Belmopan", Continent.AMERICAS, "America/Belize", "+501", "BZD"),
            WorldCountry("BM", "Bermuda", "BM", "Hamilton", Continent.AMERICAS, "Atlantic/Bermuda", "+1-441", "BMD"),
            WorldCountry("BO", "Bolivia", "BO", "La Paz / Sucre", Continent.AMERICAS, "America/La_Paz", "+591", "BOB"),
            WorldCountry(
                "BR", "Brazil", "BR", "Brasília / São Paulo", Continent.AMERICAS, "America/Sao_Paulo", "+55", "BRL",
                listOf(
                    SubTimeZoneInfo("Brasília / São Paulo / Rio (BRT)", "America/Sao_Paulo"),
                    SubTimeZoneInfo("Manaus / Amazonia (AMT)", "America/Manaus"),
                    SubTimeZoneInfo("Fernando de Noronha (FNT)", "America/Noronha"),
                    SubTimeZoneInfo("Rio Branco / Acre (ACT)", "America/Rio_Branco")
                )
            ),
            WorldCountry(
                "CA", "Canada", "CA", "Ottawa / Toronto", Continent.AMERICAS, "America/Toronto", "+1", "CAD",
                listOf(
                    SubTimeZoneInfo("Toronto / Ottawa / Montreal (Eastern)", "America/Toronto"),
                    SubTimeZoneInfo("Vancouver / Victoria (Pacific)", "America/Vancouver"),
                    SubTimeZoneInfo("Calgary / Edmonton (Mountain)", "America/Edmonton"),
                    SubTimeZoneInfo("Winnipeg (Central)", "America/Winnipeg"),
                    SubTimeZoneInfo("Halifax (Atlantic)", "America/Halifax"),
                    SubTimeZoneInfo("St. John's (Newfoundland)", "America/St_Johns")
                )
            ),
            WorldCountry("KY", "Cayman Islands", "KY", "George Town", Continent.AMERICAS, "America/Cayman", "+1-345", "KYD"),
            WorldCountry(
                "CL", "Chile", "CL", "Santiago", Continent.AMERICAS, "America/Santiago", "+56", "CLP",
                listOf(
                    SubTimeZoneInfo("Santiago (Mainland)", "America/Santiago"),
                    SubTimeZoneInfo("Easter Island", "Pacific/Easter")
                )
            ),
            WorldCountry("CO", "Colombia", "CO", "Bogotá", Continent.AMERICAS, "America/Bogota", "+57", "COP"),
            WorldCountry("CR", "Costa Rica", "CR", "San José", Continent.AMERICAS, "America/Costa_Rica", "+506", "CRC"),
            WorldCountry("CU", "Cuba", "CU", "Havana", Continent.AMERICAS, "America/Havana", "+53", "CUP"),
            WorldCountry("CW", "Curaçao", "CW", "Willemstad", Continent.AMERICAS, "America/Curacao", "+599", "ANG"),
            WorldCountry("DM", "Dominica", "DM", "Roseau", Continent.AMERICAS, "America/Dominica", "+1-767", "XCD"),
            WorldCountry("DO", "Dominican Republic", "DO", "Santo Domingo", Continent.AMERICAS, "America/Santo_Domingo", "+1-809", "DOP"),
            WorldCountry(
                "EC", "Ecuador", "EC", "Quito", Continent.AMERICAS, "America/Guayaquil", "+593", "USD",
                listOf(
                    SubTimeZoneInfo("Quito / Guayaquil (Mainland)", "America/Guayaquil"),
                    SubTimeZoneInfo("Galápagos Islands", "Pacific/Galapagos")
                )
            ),
            WorldCountry("SV", "El Salvador", "SV", "San Salvador", Continent.AMERICAS, "America/El_Salvador", "+503", "USD"),
            WorldCountry("FK", "Falkland Islands", "FK", "Stanley", Continent.AMERICAS, "Atlantic/Stanley", "+500", "FKP"),
            WorldCountry("GF", "French Guiana", "GF", "Cayenne", Continent.AMERICAS, "America/Cayenne", "+594", "EUR"),
            WorldCountry("GL", "Greenland", "GL", "Nuuk", Continent.AMERICAS, "America/Nuuk", "+299", "DKK"),
            WorldCountry("GD", "Grenada", "GD", "St. George's", Continent.AMERICAS, "America/Grenada", "+1-473", "XCD"),
            WorldCountry("GP", "Guadeloupe", "GP", "Basse-Terre", Continent.AMERICAS, "America/Guadeloupe", "+590", "EUR"),
            WorldCountry("GT", "Guatemala", "GT", "Guatemala City", Continent.AMERICAS, "America/Guatemala", "+502", "GTQ"),
            WorldCountry("GY", "Guyana", "GY", "Georgetown", Continent.AMERICAS, "America/Guyana", "+592", "GYD"),
            WorldCountry("HT", "Haiti", "HT", "Port-au-Prince", Continent.AMERICAS, "America/Port-au-Prince", "+509", "HTG"),
            WorldCountry("HN", "Honduras", "HN", "Tegucigalpa", Continent.AMERICAS, "America/Tegucigalpa", "+504", "HNL"),
            WorldCountry("JM", "Jamaica", "JM", "Kingston", Continent.AMERICAS, "America/Jamaica", "+1-876", "JMD"),
            WorldCountry("MQ", "Martinique", "MQ", "Fort-de-France", Continent.AMERICAS, "America/Martinique", "+596", "EUR"),
            WorldCountry(
                "MX", "Mexico", "MX", "Mexico City", Continent.AMERICAS, "America/Mexico_City", "+52", "MXN",
                listOf(
                    SubTimeZoneInfo("Mexico City / Central", "America/Mexico_City"),
                    SubTimeZoneInfo("Cancún (Quintana Roo)", "America/Cancun"),
                    SubTimeZoneInfo("Mazatlán / Mountain", "America/Mazatlan"),
                    SubTimeZoneInfo("Tijuana / Baja California", "America/Tijuana")
                )
            ),
            WorldCountry("MS", "Montserrat", "MS", "Plymouth / Brades", Continent.AMERICAS, "America/Montserrat", "+1-664", "XCD"),
            WorldCountry("NI", "Nicaragua", "NI", "Managua", Continent.AMERICAS, "America/Managua", "+505", "NIO"),
            WorldCountry("PA", "Panama", "PA", "Panama City", Continent.AMERICAS, "America/Panama", "+507", "PAB"),
            WorldCountry("PY", "Paraguay", "PY", "Asunción", Continent.AMERICAS, "America/Asuncion", "+595", "PYG"),
            WorldCountry("PE", "Peru", "PE", "Lima", Continent.AMERICAS, "America/Lima", "+51", "PEN"),
            WorldCountry("PR", "Puerto Rico", "PR", "San Juan", Continent.AMERICAS, "America/Puerto_Rico", "+1-787", "USD"),
            WorldCountry("KN", "Saint Kitts and Nevis", "KN", "Basseterre", Continent.AMERICAS, "America/St_Kitts", "+1-869", "XCD"),
            WorldCountry("LC", "Saint Lucia", "LC", "Castries", Continent.AMERICAS, "America/St_Lucia", "+1-758", "XCD"),
            WorldCountry("VC", "Saint Vincent & Grenadines", "VC", "Kingstown", Continent.AMERICAS, "America/St_Vincent", "+1-784", "XCD"),
            WorldCountry("SR", "Suriname", "SR", "Paramaribo", Continent.AMERICAS, "America/Paramaribo", "+597", "SRD"),
            WorldCountry("TT", "Trinidad and Tobago", "TT", "Port of Spain", Continent.AMERICAS, "America/Port_of_Spain", "+1-868", "TTD"),
            WorldCountry("TC", "Turks and Caicos Islands", "TC", "Cockburn Town", Continent.AMERICAS, "America/Grand_Turk", "+1-649", "USD"),
            WorldCountry(
                "US", "United States", "US", "Washington, D.C. / New York", Continent.AMERICAS, "America/New_York", "+1", "USD",
                listOf(
                    SubTimeZoneInfo("New York / Washington D.C. (Eastern)", "America/New_York"),
                    SubTimeZoneInfo("Chicago / Dallas (Central)", "America/Chicago"),
                    SubTimeZoneInfo("Denver / Salt Lake City (Mountain)", "America/Denver"),
                    SubTimeZoneInfo("Phoenix / Arizona (No DST)", "America/Phoenix"),
                    SubTimeZoneInfo("Los Angeles / San Francisco (Pacific)", "America/Los_Angeles"),
                    SubTimeZoneInfo("Anchorage / Alaska", "America/Anchorage"),
                    SubTimeZoneInfo("Honolulu / Hawaii", "Pacific/Honolulu")
                )
            ),
            WorldCountry("UY", "Uruguay", "UY", "Montevideo", Continent.AMERICAS, "America/Montevideo", "+598", "UYU"),
            WorldCountry("VE", "Venezuela", "VE", "Caracas", Continent.AMERICAS, "America/Caracas", "+58", "VES"),
            WorldCountry("VG", "British Virgin Islands", "VG", "Road Town", Continent.AMERICAS, "America/Tortola", "+1-284", "USD"),
            WorldCountry("VI", "U.S. Virgin Islands", "VI", "Charlotte Amalie", Continent.AMERICAS, "America/St_Thomas", "+1-340", "USD"),

            // ================= OCEANIA =================
            WorldCountry("AS", "American Samoa", "AS", "Pago Pago", Continent.OCEANIA, "Pacific/Pago_Pago", "+1-684", "USD"),
            WorldCountry(
                "AU", "Australia", "AU", "Canberra / Sydney", Continent.OCEANIA, "Australia/Sydney", "+61", "AUD",
                listOf(
                    SubTimeZoneInfo("Sydney / Melbourne / Canberra (AEST/AEDT)", "Australia/Sydney"),
                    SubTimeZoneInfo("Brisbane / Queensland (AEST)", "Australia/Brisbane"),
                    SubTimeZoneInfo("Adelaide (ACST/ACDT)", "Australia/Adelaide"),
                    SubTimeZoneInfo("Darwin (ACST)", "Australia/Darwin"),
                    SubTimeZoneInfo("Perth / Western Australia (AWST)", "Australia/Perth")
                )
            ),
            WorldCountry("CK", "Cook Islands", "CK", "Avarua", Continent.OCEANIA, "Pacific/Rarotonga", "+682", "NZD"),
            WorldCountry("FJ", "Fiji", "FJ", "Suva", Continent.OCEANIA, "Pacific/Fiji", "+679", "FJD"),
            WorldCountry("PF", "French Polynesia", "PF", "Papeete (Tahiti)", Continent.OCEANIA, "Pacific/Tahiti", "+689", "XPF"),
            WorldCountry("GU", "Guam", "GU", "Hagåtña", Continent.OCEANIA, "Pacific/Guam", "+1-671", "USD"),
            WorldCountry(
                "KI", "Kiribati", "KI", "Tarawa / Kiritimati", Continent.OCEANIA, "Pacific/Tarawa", "+686", "AUD",
                listOf(
                    SubTimeZoneInfo("Tarawa (Gilbert Islands - UTC+12)", "Pacific/Tarawa"),
                    SubTimeZoneInfo("Kiritimati / Christmas Island (UTC+14)", "Pacific/Kiritimati")
                )
            ),
            WorldCountry("MH", "Marshall Islands", "MH", "Majuro", Continent.OCEANIA, "Pacific/Majuro", "+692", "USD"),
            WorldCountry("FM", "Micronesia", "FM", "Palikir", Continent.OCEANIA, "Pacific/Pohnpei", "+691", "USD"),
            WorldCountry("NR", "Nauru", "NR", "Yaren", Continent.OCEANIA, "Pacific/Nauru", "+674", "AUD"),
            WorldCountry("NC", "New Caledonia", "NC", "Nouméa", Continent.OCEANIA, "Pacific/Noumea", "+687", "XPF"),
            WorldCountry(
                "NZ", "New Zealand", "NZ", "Wellington / Auckland", Continent.OCEANIA, "Pacific/Auckland", "+64", "NZD",
                listOf(
                    SubTimeZoneInfo("Auckland / Wellington (NZST/NZDT)", "Pacific/Auckland"),
                    SubTimeZoneInfo("Chatham Islands", "Pacific/Chatham")
                )
            ),
            WorldCountry("NU", "Niue", "NU", "Alofi", Continent.OCEANIA, "Pacific/Niue", "+683", "NZD"),
            WorldCountry("NF", "Norfolk Island", "NF", "Kingston", Continent.OCEANIA, "Pacific/Norfolk", "+672", "AUD"),
            WorldCountry("MP", "Northern Mariana Islands", "MP", "Saipan", Continent.OCEANIA, "Pacific/Saipan", "+1-670", "USD"),
            WorldCountry("PW", "Palau", "PW", "Ngerulmud", Continent.OCEANIA, "Pacific/Palau", "+680", "USD"),
            WorldCountry("PG", "Papua New Guinea", "PG", "Port Moresby", Continent.OCEANIA, "Pacific/Port_Moresby", "+675", "PGK"),
            WorldCountry("PN", "Pitcairn Islands", "PN", "Adamstown", Continent.OCEANIA, "Pacific/Pitcairn", "+64", "NZD"),
            WorldCountry("WS", "Samoa", "WS", "Apia", Continent.OCEANIA, "Pacific/Apia", "+685", "WST"),
            WorldCountry("SB", "Solomon Islands", "SB", "Honiara", Continent.OCEANIA, "Pacific/Guadalcanal", "+677", "SBD"),
            WorldCountry("TK", "Tokelau", "TK", "Fakaofo", Continent.OCEANIA, "Pacific/Fakaofo", "+690", "NZD"),
            WorldCountry("TO", "Tonga", "TO", "Nukuʻalofa", Continent.OCEANIA, "Pacific/Tongatapu", "+676", "TOP"),
            WorldCountry("TV", "Tuvalu", "TV", "Funafuti", Continent.OCEANIA, "Pacific/Funafuti", "+688", "AUD"),
            WorldCountry("VU", "Vanuatu", "VU", "Port Vila", Continent.OCEANIA, "Pacific/Efate", "+678", "VUV"),
            WorldCountry("WF", "Wallis and Futuna", "WF", "Mata-Utu", Continent.OCEANIA, "Pacific/Wallis", "+681", "XPF"),

            // ================= ANTARCTICA & POLAR ISLANDS =================
            WorldCountry(
                "AQ", "Antarctica", "AQ", "McMurdo / Amundsen-Scott", Continent.ANTARCTICA, "Antarctica/McMurdo", "+672", "USD",
                listOf(
                    SubTimeZoneInfo("McMurdo / South Pole Station", "Antarctica/McMurdo"),
                    SubTimeZoneInfo("Palmer Station", "Antarctica/Palmer"),
                    SubTimeZoneInfo("Rothera Research Station", "Antarctica/Rothera"),
                    SubTimeZoneInfo("Davis Station", "Antarctica/Davis")
                )
            ),
            WorldCountry("GS", "South Georgia & Sandwich Islands", "GS", "King Edward Point", Continent.ANTARCTICA, "Atlantic/South_Georgia", "+500", "GBP"),
            WorldCountry("CX", "Christmas Island", "CX", "Flying Fish Cove", Continent.OCEANIA, "Indian/Christmas", "+61", "AUD"),
            WorldCountry("CC", "Cocos (Keeling) Islands", "CC", "West Island", Continent.OCEANIA, "Indian/Cocos", "+61", "AUD")
        )

        // Ensure every country has a valid available TimeZone on the current Android runtime,
        // and also discover any remaining ISO countries from Locale.getISOCountries() so no country on Earth is missed.
        val availableZoneSet = TimeZone.getAvailableIDs().toSet()
        val curatedMap = LinkedHashMap<String, WorldCountry>()
        for (c in curated) {
            val resolvedPrimary = resolveZoneId(c.primaryZoneId, availableZoneSet)
            curatedMap[c.isoCode] = c.copy(primaryZoneId = resolvedPrimary)
        }

        for (iso in Locale.getISOCountries()) {
            if (!curatedMap.containsKey(iso)) {
                val locale = Locale("", iso)
                val displayCountry = locale.getDisplayCountry(Locale.ENGLISH)
                if (displayCountry.isNotBlank() && displayCountry != iso) {
                    val matchedZone = availableZoneSet.firstOrNull {
                        it.endsWith("/" + displayCountry.replace(" ", "_"), ignoreCase = true)
                    } ?: "UTC"
                    val continent = inferContinentFromZone(matchedZone)
                    curatedMap[iso] = WorldCountry(
                        id = iso,
                        countryName = displayCountry,
                        isoCode = iso,
                        capitalOrHub = matchedZone.substringAfterLast('/').replace('_', ' '),
                        continent = continent,
                        primaryZoneId = matchedZone,
                        callingCode = "ISO-$iso",
                        currencyCode = "—"
                    )
                }
            }
        }

        return curatedMap.values.sortedBy { it.countryName.lowercase(Locale.ENGLISH) }
    }

    private fun resolveZoneId(preferredId: String, availableIds: Set<String>): String {
        if (availableIds.contains(preferredId)) return preferredId
        // Common Android IANA aliases
        val aliasMap = mapOf(
            "Europe/Kyiv" to "Europe/Kiev",
            "America/Nuuk" to "America/Godthab",
            "Asia/Kolkata" to "Asia/Calcutta",
            "Asia/Ho_Chi_Minh" to "Asia/Saigon",
            "Asia/Yangon" to "Asia/Rangoon",
            "Asia/Kathmandu" to "Asia/Katmandu",
            "Atlantic/Faroe" to "Atlantic/Faeroe"
        )
        val fallback = aliasMap[preferredId]
        if (fallback != null && availableIds.contains(fallback)) return fallback
        return preferredId
    }

    private fun inferContinentFromZone(zoneId: String): Continent {
        return when {
            zoneId.startsWith("Asia/") -> Continent.ASIA
            zoneId.startsWith("Europe/") -> Continent.EUROPE
            zoneId.startsWith("Africa/") -> Continent.AFRICA
            zoneId.startsWith("America/") -> Continent.AMERICAS
            zoneId.startsWith("Australia/") || zoneId.startsWith("Pacific/") -> Continent.OCEANIA
            zoneId.startsWith("Antarctica/") -> Continent.ANTARCTICA
            zoneId.startsWith("Indian/") -> Continent.AFRICA
            zoneId.startsWith("Atlantic/") -> Continent.EUROPE
            else -> Continent.EUROPE
        }
    }
}
