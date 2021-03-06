package bundles;

import java.util.ListResourceBundle;

public class Resources_sk extends ListResourceBundle {
    private static final Object[][] contents = {
            {"enter","Enter"},
            {"registration","Registrácia"},
            {"exit","Exit"},
            {"flats","Apartmány"},
            {"logging","Log in"},
            {"login","Prihlásiť sa"},
            {"password","Heslo"},
            {"back","Späť"},
            {"register","Register"},
            {"settings","Nastavenia"},
            {"language","Jazyk"},
            {"change_user","Zmeniť používateľa"},
            {"help","Pomoc pre príkazy"},
            {"info","Informácie o zbere"},
            {"show","Zobraziť celú kolekciu"},
            {"add","Pridať Apartmán"},
            {"clear","Vymazať zbierka"},
            {"remove_last","Odstrániť posledný"},
            {"average","Priemerná hodnota \"obytný priestor\""},
            {"max","Max podľa roku výstavby"},
            {"update","Aktualizovať byt"},
            {"remove_by_id","Odstrániť podľa ID"},
            {"remove_at","Odstrániť podľa indexu"},
            {"execute_script","Spustiť skript"},
            {"filter","Filtrovať podľa zobrazenia"},
            {"list","Zoznam apartmánov"},
            {"visualization","Vizualizácia"},
            {"name","Meno vlastníka"},
            {"coordinates","Súradnice"},
            {"area","oblasť"},
            {"number_of_rooms","Počet izieb"},
            {"living_space","Obytný priestor"},
            {"flat","Byt"},
            {"view","Pohľad z okna"},
            {"transport","Doprava"},
            {"house_name","Názov domu"},
            {"house_year","Rok bol dom postavený"},
            {"number_of_flats","Počet bytov na poschodí"},
            {"type","Enter"},
            {"nameErr","Názov musí byť neprázdny reťazec"},
            {"xErr","Súradnica X musí byť číslo"},
            {"yErr","Súradnica Y musí byť číslo do 368"},
            {"areaErr","Obytný priestor musí byť kladné číslo"},
            {"numberErr","Počet miestností musí byť kladné celé číslo"},
            {"livingErr","Plocha obývacích izieb musí byť kladné číslo"},
            {"hnameErr","Názov musí byť neprázdny reťazec"},
            {"hyearErr","Rok, v ktorom bol dom postavený, musí byť kladné celé číslo"},
            {"hnumberErr","Počet bytov na poschodí musí byť kladné celé číslo"},
            {"idErr","Chyba! 'id' musí byť kladné celé číslo.\n Opakujte príkaz."},
            {"indexErr","Chyba! Index musí byť nezáporné celé číslo.\n Opakujte príkaz."},
            {"scriptErr","Cesta musí byť neprázdny reťazec"},
            {"viewErr","Chyba. Zadali ste neplatnú hodnotu zobrazenia.\n"},
            {"showButton","Dramdrum"},
            {"creationDate","Culbon\nbon"},
            {"house","Dom informácie"},
            {"id","ID"},
            {"user","User"}


    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
