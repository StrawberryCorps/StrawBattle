# StrawBattle

[![Download][download-shield]][download-url]
[![Forks][forks-shield]][forks-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

[forks-shield]: https://img.shields.io/github/forks/StrawberryCorps/StrawBattle?style=for-the-badge
[forks-url]: https://github.com/StrawberryCorps/StrawBattle/network/members
[download-shield]: https://img.shields.io/github/downloads/StrawberryCorps/StrawBattle/total?style=for-the-badge
[download-url]: https://github.com/StrawberryCorps/StrawBattle/
[issues-shield]: https://img.shields.io/github/issues/StrawberryCorps/StrawBattle?style=for-the-badge
[issues-url]: https://github.com/StrawberryCorps/StrawBattle/issues
[license-shield]: https://img.shields.io/github/license/StrawberryCorps/StrawBattle?style=for-the-badge
[license-url]: https://github.com/StrawberryCorps/StrawBattle/blob/main/LICENSE

---

## Installation rapide 

Pour installer le jeu vous devez d'abord créer un serveur et dans le dossier "plugins" vous devez y insérer le fichier .jar que vous venez de télécharger dans la page téléchargement. 

Lancer une fois votre serveur afin de générer le fichier de configuration. **Attention** une erreur ce produira au premier lancement en effet il faut configurer les maps de jeu

Pour configurer les maps de jeu vous trouverez à votre disposition dans le fichier .zip fourni des maps faites par [Zennit_](https://www.planetminecraft.com/member/zennit_/) ainsi qu'une préconfiguration que vous pouvez bien sûr modifier comme vous le souhaitez. Il vous suffit de mettre le dossier des maps dans le dossier StrawBattle dans le dossier "plugins". Vous y verrez un fichier config.yml voici un exemple de configuration pour utiliser toutes les maps du fichier .zip, vous pouvez mettre vos propres maps et les configurer.

```yml
min-players: 6
game-prefix: "§9[§3StrawBattle§9]§r "
spawn:
  world: world
  x: 0.5
  y: 80
  z: 0.5
  yaw: 0
  pitch: 0
maps:
  Rainbow:
    cuboid: -45.5,255,-45.5:45.5,0,45.5
  Cuboid:
    cuboid: 60.5,255,-40.5:-40.5,0,60.5
  Cake:
    cuboid: -40.5,255,0.5:40.5,0,70.5
```

Pour forcer le lancement d'une partie une personne ayant la permission ``strawbattle.forcestart`` pourra faire la commande ``/forcestart``.

## Personnalisation

Vous pouvez mettre vos propres cartes de jeu. Pour les positions de départ merci de mettre une balise sur le point de spawn du joueur ainsi qu'une protection en verre au point de position, sans celle-ci l'algorithme ne pourra pas trouver les points de spawn.

## Vidéo 

@TODO

## Contribuer

1. Fork le projet
2. Créez votre branche de fonctionnalités (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers votre branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une pull request

## License

Distribué sous la licence MIT. Voir [LICENSE](LICENSE) pour plus d'informations.