# :label: Semestrální projekt z předmětu Objektový návrh a modelování  (OMO)

![Smarthome](https://gitlab.fel.cvut.cz/krossale/omo-pro/-/raw/main/images/home.png)

**Code**: B6B36OMO <br>
**Name**: Object-oriented design and Modeling/ Objektový návrh a modelování <br>
**Faculty**: Faculty of Electrical Engineering/ Fakulta elektrotechnická <br>
**Department**: 13136 - Department of Computer Science/ Katedra počítačů <br>

## :label: Zadání
<details><summary> This repository contains the code for a virtual simulation application of a smart home. The application is designed to simulate the operations of a household, using various home appliances, and evaluate the utilization, consumption, and time distribution (both working and leisure) of its inhabitants. </summary>
<br></details>


## :label: Description
The application is part of a project undertaken at the Faculty of Electrical Engineering, specifically within the Department of Computer Science (13136 - Katedra počítačů). It aims to create a complex simulation interacting with various entities such as houses, windows, blinds, floors, sensors, appliances, and even animals, to demonstrate the functioning of a smart home environment.

## :label: Features
**API Integration for Home Devices** Control and monitor devices using their respective APIs. Devices have states that can be modified accordingly.
**Data Collection** Collect data on electricity, gas, and water consumption as well as device functionality over time.
**Event Handling** Process events based on the type of event and the entity handling it. Examples include handling power outages, water leaks, and food shortages.
**Activity Simulation** Simulate daily activities of humans and pets affecting other entities or devices within the home.
**Report Generation** Create various reports such as house configuration, event logs, activity and usage summaries, and consumption metrics.
**Vytvořit aplikaci pro virtuální simulaci inteligentního domu, kde simulujeme chod domácnosti, používáme jednotlivá zařízení domu a vyhodnocujeme využití, spotřebu, volný a pracovní čas jednotlivých osob.** <br><br>
[**Odkaz na zadaní**](https://docs.google.com/document/d/1Y3EAonMWISGIhRqEhg9kaIxJZLXauUrZPiCyVq_LtEg/)



## :label: Funkční požadavky

**F1.** Entity se kterými pracujeme je dům, okno (+ venkovní žaluzie), patro v domu, senzor, zařízení (=spotřebič), osoba, auto, kolo, domácí zvíře jiného než hospodářského typu, plus libovolné další entity. <br>

**F2.** Jednotlivá zařízení v domu mají API na ovládání. Zařízení mají stav, který lze měnit pomocí API na jeho ovládání. Akce z API jsou použitelné podle stavu zařízení. Vybraná zařízení mohou mít i obsah - lednice má jídlo, CD přehrávač má CD.<br>

**F3.** Spotřebiče mají svojí spotřebu v aktivním stavu, idle stavu, vypnutém stavu<br>

**F4.** Jednotlivá zařízení mají API na sběr dat o tomto zařízení. O zařízeních sbíráme data jako spotřeba elektřiny, plynu, vody a funkčnost (klesá lineárně s časem)<br>

**F5.** Jednotlivé osoby a zvířata mohou provádět aktivity(akce), které mají nějaký efekt na zařízení nebo jinou osobu. Např. Plynovy_kotel_1[oteverny_plyn] + Otec.zavritPlyn(plynovy_kotel_1) -> Plynovy_kotel_1[zavreny_plyn].<br>

**F6.** Jednotlivá zařízení a osoby se v každém okamžiku vyskytují v jedné místnosti (pokud nesportují) a náhodně generují eventy (eventem může být důležitá informace a nebo alert)<br>

**F7.** Eventy jsou přebírány a odbavovány vhodnou osobou (osobami) nebo zařízením (zařízeními). Např.:
- čidlo na vítr (vítr) => vytažení venkovních žaluzií
- jistič (výpadek elektřiny) => vypnutí všech nedůležitých spotřebičů (v provozu zůstávají pouze ty nutné)
- čidlo na vlhkost (prasklá trubka na vodu) => máma -> zavolání hasičů, táta -> uzavření vody;dcera -> vylovení křečka
- Miminko potřebuje přebalit => táta se skrývá, máma -> přebalení
- Zařízení přestalo fungovat => …
- V lednici došlo jídlo => ...<br>

**F8.** Vygenerování reportů:
- HouseConfigurationReport: veškerá konfigurační data domu zachovávající hieararchii - dům -> patro -> místnost -> okno -> žaluzie atd. Plus jací jsou obyvatelé domu.
- EventReport: report eventů, kde grupujeme eventy podle typu, zdroje eventů a jejich cíle (jaká entita event odbavila)
- ActivityAndUsageReport: Report akcí (aktivit) jednotlivých osob a zvířat, kolikrát které osoby použily které zařízení.
- ConsumptionReport: Kolik jednotlivé spotřebiče spotřebovaly elektřiny, plynu, vody. Včetně finančního vyčíslení.
<br>

**F9.** Při rozbití zařízení musí obyvatel domu prozkoumat dokumentaci k zařízení - najít záruční list, projít manuál na opravu a provést nápravnou akcí (např. Oprava svépomocí, koupě nového atd.). Manuály zabírají mnoho místa a trvá dlouho než je najdete. Hint: Modelujte jako jednoduché akce ...dokumentace je přístupná jako proměnná přímo v zařízení, nicméně se dotahuje až, když je potřeba.<br>

**F10.** Rodina je aktivní a volný čas tráví zhruba v poměru (50% používání spotřebičů v domě a 50% sport kdy používá sportovní náčiní kolo nebo lyže). Když není volné zařízení nebo sportovní náčiní, tak osoba čeká.



## :label: Nefunkční požadavky

- Není požadována autentizace ani autorizace<br>
- Aplikace může běžet pouze v jedné JVM<br>
- Aplikaci pište tak, aby byly dobře schované metody a proměnné, které nemají být dostupné ostatním třídám. Vygenerovný javadoc by měl mít co nejméně public metod a proměnných.<br>
- Reporty jsou generovány do textového souboru<br>
- Konfigurace domu, zařízení a obyvatel domu může být nahrávána přímo z třídy nebo externího souboru (preferován je json).


## :label: Použité design patterny:
- Bridge	
- Builders	
- Composite	
- Facade	
- Factories	
- Observer	
- State	
- Visitor	
- Entity
- Subject


## :label: Zkušenosti získané během SP

<details><summary> Show more </summary><br>

Začali jsme tvorbou diagramu, který následně prošel několika proměnami. Dalé následovalo vytvoření kódu, laděného podle našeho vytvořeného diagramu. Během projektu prošly naše modely řadou vylepšení.<br>

Realizace všech Design Patternů pro nás byla novou zkušeností. Díky konzultacím s učitelem jsme neustále zdokonalovali náš projekt a každá setkání s ním přinášela viditelný posun vpřed. Měli jsme také možnost konzultovat s učitelem i na cvičení.<br>

S ohledem na naši předchozí nezkušenost s Design Patterny to bylo náročné, ale přesto jsme věnovali veškeré úsilí tomu, abychom se s touto novou výzvou vyrovnali. <br>

Celkově nás tato práce na projektu nadchla. Hlavním ponaučením pro mě bylo zjištění, že používání Design Patternů jako konceptuálního řešení konkrétních problémů nejen šetří čas, ale zároveň zvyšuje efektivitu a minimalizuje riziko vzniku chyb.


</details>


## :label: Contacts

**Cvičící:** Jiří Šebek <br>

**Zpracoval:** Mikhailo Kononenko **[📧](mailto:kononmi1@fel.czut.cz)**<br>

