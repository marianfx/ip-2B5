# ip-2B5
[![slack shield mock](https://img.shields.io/badge/slack-available-ff69b4.svg?style=flat)](https://ip-b5.slack.com/messages/)

Acesta este repo-ul grupei 2B5. :fireworks: :rocket: :stars:

**Descriere proiect**
> inca nu exista o descriere oficiala a proiectuui

###Structura
<!--- + [link](##Calendar)
-->
+ [Calendar](#calendar-anchor)
+ [Metodologie de lucru](#metodologie-lucru-anchor)
+ [Explicatii suplimentare workflow](#explicatii-workflow-anchor)
+ Questions - Edge cases:
  + git clone copiaza toate remotes - [link](#chestii-discutabile1)

<a name="calendar-anchor"></a>

##Calendar

:thought_balloon:  __Saptamana 7__ - [descriere laborator](http://profs.info.uaic.ro/~adiftene/Scoala/2016/IP/Laboratoare/Lab07.pdf)
... `deadline: 06.04.2016, ora 10 (S8)`
+ [x] requirements analysis (cu punctaj)
+ [x] informatii utile pe github


:thought_balloon:  __Saptamana 9__ - [descriere laborator](http://profs.info.uaic.ro/~adiftene/Scoala/2016/IP/Laboratoare/Lab09.pdf)
... `deadline: 19.04.2016, ora 10 (S10)`
+ [ ] diagrame UML
+ [ ] coordonare ...


<a name="metodologie-lucru-anchor"></a>

##Metodologie de lucru

Fiecare din cele 3 echipe isi va face un fork al acestui repo din interfata Github.

**Numele de pull-requesturi sau commituri trebuie sa respecte niste norme !** (verificati commituri/pull requests anterioare pe repo-ul principal)

**Instalati urmatoarele:**
+ https://desktop.github.com/ - client de desktop pt Github (nu folositi decat pentru vizualizare de diffuri, **nu dati sync pentru ca veti crea probleme !**)
+ https://git-for-windows.github.io/ - bash de git care se poate suprapune peste cmd.exe, la instalare:
  +  Alegeti calea de mijloc - run git from the windows command prompt (nu da override la windows, si poti rula toate comenzile din git si din git bash - cygwin, i presume - si din cmd.exe)
  +  Nu instalati credential manager - e folosit pentru a va autoriza push-ul, de exemplu - este o fereastra separata de cmd.exe si va dura mult pana se deschide

####I. Setati remote (one time only)
--**sefii de echipa** - faceti asta o singura data
+ `git remote -v` //verifica ce remote sunt setate, by default fiind origin, cel pentru repo-ul echipei voastre (fork-ul)
<a name="chestii-discutabile1"></a>
> Q1: **o data ce seful de echipa a setat un remote si a facut push, teoretic, daca voi ati clonat local repo-ul dupa acel push, ar trebui sa aveti remote-ul de upstream (git remote -v)**

+ `git remote add upstream https://github.com/2B5/ip-2B5` //adauga un nou remote pentru upstream, astfel incat voi sa puteti rula urmatoarele comenzi

####II. Sincronizati cu repo-ul principal (de fiecare data cand vreti sa faceti un pull request)
--**sefii de echipa** - de fiecare data cand vreti sa faceti un pull request catre repo-ul principal (cu alte cuvinte, vreti sa faceti "push" in repo-ul principal)

+ `git fetch` //preia schimbarile din upstream
+ `git rebase upstream/master` //adauga schimbarile din upstream si pozitioneaza modificarile actuale peste cele din upstream; un exemplu:
```
  Before rebase
  > ... o ---- o ---- A ---- B  origin/master (upstream)
  >                    \      
  >                     C ---- M  master (origin)
  After rebase
  > ... o ---- o ---- A ---- B  origin/master (upstream)
  >                           \      
  >                            C ---- M  master (origin)
```
+ `git push origin master` //daca nu este nimic modificat in repo-ul vostru fata de upstream / ->
> **Edge cases here !:** daca aveti commits care nu au fost pushed ?, daca aveti modificari care nu au fost add / sau nu au fost added, dar nu fac parte dintr-un commit ? ...

O data ce ati terminat ce ati avut de facut, faceti un pull request catre repo-ul principal (nu e nevoie sa modificati nimic la cerere, decat un **titlu si o descriere conforme cu normele stabilite** - verificati pull requesturi anterioare sau intrebati pe [Dan](https://github.com/xR86)) ~~ **normele vor fi incluse in curand si aici**

####III. Sincronizati cu repo-ul echipei voastre (de fiecare data cand vreti sa adaugati ceva pe repo-ul vostru)
--**toti !**
+ `git rebase origin/master` //pentru a va adauga modificarile dupa cele ale echipei voastre (**presupun ca aici s-ar putea sa apara probleme la partea de implementare, la un moment dat**)
+ `git status` //verifica daca sunteti repo-ul local este in urma/fata repo-ului de pe github (al vostru), verifica daca au fost schimbate fisiere
+ `git add .` //daca sunt fisiere modificate/untracked, schimbarile vor fi salvate recursiv pe repo (orice (sub)folder sau fisier modificat sau adaugat)
+ `git commit -m "<mesaj>"`//orice fisier adaugat prin add va fi inclus in acest commit; <mesaj> este un tag care poate fi inlocuit cu orice string; respectati politica de mesaje !
+ `git push origin master` //commitul va fi incarcat pe remote-ul de origin (repo-ul vostru) pe branchul master (grija la branchuri, daca tineti neaparat !)

-- in caz in care aveti modificari la care inca nu s-a dat commit, si nu se poate face rebase:
+ `git stash` - salveaza schimbarile actuale fata de HEAD intr-o locatie separata
+ `git rebase origin/master` //descris mai inainte
+ `git stash apply` - restaureaza
+ apoi git add, commit, push, cam mai inainte

####Comenzi extra:
+ `git log` \\verifica commiturile anterioare (commit history, basically) - navigati cu tastele directionale, apasati 'q' si apoi dati enter pentru iesire
+ `git diff` \\verifica modificarile fata de ultimul commit - acelasi mod de lucru ...
+ `git reset` \\in forma asta, git reset face un 'undo' la git add, daca nu ati facut commit-ul inca, si ati mai adaugat ceva dupa git add
+ extra la extra:
  + `git rebase HEAD~<nr>` \\intoarce istoricul din repo cu **nr** pasi inapoi | **nu folositi aceasta comanda decat daca ati folosit in prealabil comanda pe un repo de test !** (pentru ca `git rebase --continue` si `git rebase --abort`)


<a name="explicatii-workflow-anchor"></a>

##De ce am ales acest workflow ?

+ Lucratul pe branchuri ar fi dus la confuzii mai multe, si la conflicte la push. De asemenea, ar fi aparut probleme, de vreme ce master nu are un control suficient de granular pentru protectia branchului.
+ Fiecare echipa are un repo personal care poate fi sincronizat cu cel principal oricand prin remotes (pull/push) sau poate avea un istoric de mai multe commituri inainte sa faca push pe cel principal (din motive de control al istoricului - git squash - sau ...)
