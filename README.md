# CarbitLab HelloWorld (prototype diagnostic)

## But
Tester si CarbitLink peut rendre une application tierce **sans projection d'écran**, et si son lecteur intégré reconnaît une session multimédia Android ordinaire.

**Important : ce prototype n'implémente pas Android Auto, ne contourne pas CarbitLink et ne modifie pas le système de la voiture.** L'APK est un lanceur Android classique qui affiche « HELLO WORLD » sur le téléphone et peut publier un titre « HELLO WORLD » à travers `MediaSession` avec un très faible son de test.

## Compiler une APK

### Option Android Studio
1. Ouvrir le dossier de ce projet (`File > Open`).
2. Laisser Android Studio synchroniser Gradle.
3. `Build > Build Bundle(s) / APK(s) > Build APK(s)`.
4. L'APK se trouvera dans `app/build/outputs/apk/debug/app-debug.apk`.

### Option GitHub Actions gratuite
1. Créer un dépôt GitHub contenant **le contenu** de ce dossier.
2. Onglet `Actions` > `Build APK` > `Run workflow`.
3. Après succès : `Artifacts` > `CarbitLab-HelloWorld-debug-apk`.
4. Extraire l'archive pour obtenir `app-debug.apk`.

Note : aucun APK précompilé n'est inclus ; l'environnement de création de ce projet n'avait pas de SDK Android. Le code n'a pas été testé par compilation.

## Protocole de test (voiture à l'arrêt)

1. Installer l'APK sur le téléphone. Elle apparaît sous le nom **AA Lab Hello**.
2. Connecter CarbitLink sans activer `Image miroir`. Ouvrir le `Gestionnaire des applications` : vérifier si `AA Lab Hello` apparaît.
3. Si oui, l'ajouter, lancer l'icône et noter si un petit badge bleu apparaît ou si la voiture réclame le partage d'écran.
4. Sur le téléphone, ouvrir `AA Lab Hello` et appuyer sur `Activer le test media HELLO WORLD`.
5. Sans partage d'écran, ouvrir les fonctions musicales natives de CarbitLink (notamment `Musique locale`) et vérifier si `HELLO WORLD` apparaît dans les métadonnées / commandes.
6. Verrouiller le téléphone pendant le test média et voir si la lecture reste visible dans la voiture. Arrêter ensuite le test depuis l'appli ou la notification.

## Interprétation prudente
- `AA Lab Hello` dans le gestionnaire **avec badge bleu** : CarbitLink connaît l'appli mais la traite comme une application à projeter.
- `HELLO WORLD` dans la partie musique **sans badge bleu** : une certaine intégration générique MediaSession/AVRCP est possible, **pas** un rendu d'interface graphique arbitraire.
- Aucun affichage : pas de preuve que c'est impossible, seulement que le protocole testé ne suffit pas.
- Le cas Spotify peut relever d'une intégration spéciale CarbitLink, indépendante des applications Android ordinaires.

## Sécurité
Aucun accès réseau, aucun root, aucune permission de superposition et aucune permission d'accessibilité. Le service média utilise un fichier audio local à bas volume et une notification persistante pendant le test. Tester voiture stationnée.
