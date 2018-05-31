package FriendsGroup.pro;

public class Abonent {

    String getMO (String Number){
        String response = "";
        switch (Number.substring(0, 2)) {
            case "10":
                switch (Integer.parseInt(Number)) {
                    case 10001:
                    case 10002:
                    case 10003:
                    case 10004:
                    case 10005:
                    case 10006:
                    case 10007:
                    case 10008:
                    case 10009:
                    case 10010:
                    case 10011:
                    case 10012:
                    case 10013:
                    case 10014:
                    case 10015:
                    case 10016:
                    case 10017:
                    case 10018:
                    case 10019:
                    case 10052:
                    case 10053:
                    case 10054:
                    case 10055:
                    case 10056:
                    case 10057:
                    case 10058:
                    case 10059:
                    case 10060:
                    case 10061:
                    case 10051:
                    case 10062:
                    case 10073:
                    case 10072:
                    case 10074:
                    case 10075:
                    case 10076:
                    case 10077:
                    case 10064:
                        response = "ЦОВ";
                        break;

                    case 901:
                    case 10028:
                    case 10029:
                        response = "ДДС 01 Кемеровский ГО";
                        break;

                    case 902:
                    case 10030:
                    case 10031:
                    case 10063:
                        response = "ДДС 02 Кемеровский ГО";
                        break;

                    case 903:
                    case 10032:
                    case 10033:
                        response = "ДДС 03 Кемеровский ГО";
                        break;

                    case 904:
                    case 10034:
                    case 10035:
                        response = "ДДС 04 Кемеровский ГО";
                        break;

                    case 10038:
                    case 10039:
                        response = "ФСБ";
                        break;

                    case 10040:
                    case 10041:
                        response = "ЦУКС";
                        break;

                    case 905:
                    case 10036:
                    case 10037:
                        response = "ДДС 05 Кемеровский ГО";
                        break;

                    case 10042:
                    case 10043:
                        response = "ДДС СОКРЖ Кемеровский ГО";
                        break;

                    case 10024:
                    case 10025:
                    case 10020:
                    case 10021:
                    case 10044:
                    case 10045:
                    case 10046:
                    case 10049:
                    case 10065:
                    case 10078:
                    case 10079:
                    case 10080:
                    case 10081:
                    case 10082:
                    case 10083:
                    case 10084:
                    case 10085:
                    case 10086:
                    case 10087:
                    case 10088:
                    case 10089:
                    case 10090:
                    case 10091:
                    case 10092:
                    case 10093:
                    case 10094:
                    case 10095:
                    case 10047:
                        response = "ЕДДС Кемеровский ГО";
                        break;

                    case 10048:
                    case 10050:
                        response = "Тестовый аккаунт";
                        break;

                    case 10070:
                    case 10071:
                        response = "ДДС 02 Прокопьевский МР";
                        break;

                    case 10067:
                    case 10066:
                        response = "ЕДДС Прокопьевский МР";
                        break;

                    case 10069:
                    case 10068:
                        response = "ДДС 01 Прокопьевский ГО";
                        break;

                    case 10097:
                    case 10096:
                        response = "ЕДДС Беловский МР";
                        break;

                    case 10098:
                    case 10099:
                        response = "ЕДДС Краснобродский ГО";
                        break;

                    case 10100:
                    case 10101:
                        response = "ЕДДС Междуреченский ГО";
                        break;

                    case 10102:
                    case 10103:
                        response = "ЕДДС Мысковский ГО";
                        break;

                    case 10104:
                    case 10105:
                        response = "ЕДДС Новокузнецкий МР";
                        break;
                }
                break;

            case "20":
                response = "ЦОВ"; break;
            case "22":
                response = " Кемеровский ГО"; break;
            case "23":
                response = " Кемеровский МР"; break;
            case "24":
                response = " Новокузнецкий ГО"; break;
            case "25":
                response = " Новокузнецкий МР"; break;
            case "26":
                response = " Анжеро-Судженский ГО"; break;
            case "27":
                response = " Беловский ГО"; break;
            case "28":
                response = " Беловский МР"; break;
            case "29":
                response = " Киселёвский ГО"; break;
            case "30":
                response = " Ленинск-Кузнецкий ГО"; break;
            case "31":
                response = " Ленинск-Кузнецкий МР"; break;
            case "32":
                response = " Междуреченский ГО"; break;
            case "33":
                response = " Осинниковский ГО"; break;
            case "34":
                response = " Прокопьевский ГО"; break;
            case "35":
                response = " Прокопьевский МР"; break;
            case "36":
                response = " Юргинский ГО"; break;
            case "37":
                response = " Юргинский МР"; break;
            case "38":
                response = " Березовский ГО"; break;
            case "39":
                response = " Гурьевский ГО и МР"; break;
            case "40":
                response = " Мысковский ГО"; break;
            case "41":
                response = " Тайгинский ГО"; break;
            case "42":
                response = " Таштагольский МР"; break;
            case "43":
                response = " Полысаевский ГО"; break;
            case "44":
                response = " Калтанский ГО"; break;
            case "45":
                response = " Краснобродский ГО"; break;
            case "46":
                response = " Мариинский ГО и МР"; break;
            case "47":
                response = " Топкинский ГО и МР"; break;
            case "48":
                response = " Ижморский МР"; break;
            case "49":
                response = " Крапивинский МР"; break;
            case "50":
                response = " Промышленновский МР"; break;
            case "51":
                response = " Тисульский МР"; break;
            case "52":
                response = " Тяжинский МР"; break;
            case "53":
                response = " Чебулинский МР"; break;
            case "54":
                response = " Яйский МР"; break;
            case "55":
                response = " Яшкинский МР"; break;
        }
    return response;
    }

    String getDDS (String Number) {
        String response = "";

        if (Integer.parseInt(Number.substring(0, 2)) >= 22) {
            switch (Number.substring(2, 3)){
                case "0":
                    response = "ЕДДС";
                    break;
                case "1":
                    response = "ДДС 01";
                    break;
                case "2":
                    response = "ДДС 02";
                    break;
                case "3":
                    response = "ДДС 03";
                    break;
                case "4":
                    response = "ДДС 04";
                    break;
                case "5":
                    response = "ДДС 05";
                    break;
                case "6":
                    response = "ФСБ";
                    break;
                case "7":
                    response = "ЦУКС";
                    break;
                case "8":
                    response = "ДДС СОКРЖ";
                    break;
                case "9":
                    response = "";
                    break;
            }
        } else {
            response = "";
        }
        return response;
    }

    String getNameAbonent(String Number) {
        String response = Number;

        if (Number.length() <= 5) {
            response = getDDS(Number) + getMO(Number);
        }

        return response;
    }

//    String getDirection(String DNumber, String CNumber) {
//        int response;
//        if (getNameAbonent(DNumber) == "ЦОВ" && getNameAbonent(CNumber) == CNumber) {
//            response = "ЦОВ > Ростелеком"
//        }
//
//        if (getNameAbonent(DNumber) != "ЦОВ" && getNameAbonent(CNumber) == CNumber) {
//            response = 0; // ЦОВ > Ростелеком
//        }
//
//        return response;
//    }
}
