# SuperFlash
Spring boot flashcard application

## Comment utiliser git :

placer vous dans votre branch : `git checkout <ton nom>`

quand tu veux envoyer tes changements : 
```
git add .
git commit -m "ton message"
git push origin <ton nom>
```


mettre à jour ta branch avec les changements des autres: 

```
git checkout main
git pull origin main
git checkout <ton nom>
git merge master
```

mettre à jour ta branch si tu as du codes qui n'a pas était push : 

```
git stash
git pull origin main
git stash pop
```
