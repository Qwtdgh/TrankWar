TankWar
=======

## 说在前面

用J2SE写的一个坦克大战小游戏，巩固一下java基础

清理内存时突然想起来的，尘封许久的记忆，终于可以在忙碌的生活中停一下脚步，玩一下大二做的小游戏。在此感谢zzk、cms、lhx、zhy、wzx、wcy等人的试玩，真是感慨万千啊。

记录：2022/6/5 如家旅馆集中隔离

## 设计文档

### 简介

坦克大战双人游戏，击毁全部的敌方坦克便会进入下一关，通过一定的局数便会通关。

#### 难度提升

每关的难度会有所提升，具体体现在敌方坦克移动的速度、发弹的频率上、坦克的数量和坦克出现的范围，并且会随机更新地图。

#### 血包

血包会在每局开始时一定概率的在随机位置出现，其中概率逐渐提高，己方坦克吃到血包后会增加一定的血量。

#### 吸血鬼模式

当己方坦克击毁敌方坦克时，会恢复一定的血量，和被敌方子弹击中后血量会下降，为零时坦克死亡。

#### 墙壁

子弹打到墙壁之后会消失，并且敌人坦克不会穿越墙壁，而己方坦克可以穿越墙壁。当子弹超出屏幕范围子弹消失。

#### 无敌时间

每关开始时，己方坦克在一定时间内无敌，此时即使被敌人坦克击中，仍然不会掉血。

#### 战局限时

每一关都会有一定的时间限制，随局数的增加，时间逐渐延长。若没有在限制时间内将敌人坦克全部击毁，判定为游戏失败。

### 新增内容

#### 随机产生的墙壁

简介：为了增加游戏的趣味性，我设施了每一局会改变地图，也就是每局会产生不同的墙壁，墙壁的位置和长度都是随机的。也就是每局刚刚开始的时候，地图就会变化。

原理：我用链表存储敌人坦克，当敌方坦克被击毙后，将它从链表中清除，进入generateEnemy（产生新敌人）模块，在此之中往墙链表中添加新的墙，并利用nextInt（产生一个一定范围之内的随机数）产生每一个墙的随机位置和随机的长度。

#### 保证子弹碰到墙壁会消失

简介：不论是herotank 还是 enemytank发出的知道，再碰到Wall之后都会消失，并且在之后不会在产生作用。

原理：利用getRct().intersects(w.getRect())来判断子弹是否与Wall相交，要是香蕉的话，则tc.missiles.remove(this)。

#### 将子弹和坦克的颜色关联

简介：我把游戏中的坦克分为三种颜色，其中enemytank（敌人坦克）为白色，herotank1（player1）为黄色，herotank2（player2）为蓝色；他们会发出与之颜色对应的子弹。并且保证发出去的子弹只有在超出游戏屏幕范围之后才会消失。

原理：新建一个Missile（子弹）链表，发射一个子弹就将子弹加入到这个链表中，其中子弹的属性包括role（区分是enemytank还是herotank）；player（区分是player1还是player2），以及x,y（确定子弹的位置），Color（子弹的颜色），tf（发射子弹的坦克的引用）。Color = tf.Color，将发射子弹的坦克的颜色传递给子弹。

#### 控制爆炸的方位和时间，以及爆炸的颜色

简介：控制爆炸的位置，是指以子弹与坦克的接触点为圆心，在一定的时间内有小圆变成大圆，并最后消失，而且保证爆炸的颜色与子弹的颜色相同。

原理：首先是检测到子弹与坦克相交之后，进入Explode模块，通过step * DIAMETER > LIMIT; step ++ ;来控制从小圆变成大圆的时间，用g.fillOval(x – DIAMETER * STEMP/2, y – DIAMETER * STEMP/2，DIAMETER * STEMP，DIAMETER * STEMP  )来控制圆心的位置，并画出逐渐变大的圆。

#### 吸血模式

简介：当herotank1 或是 herotank2 击毁 enemytank后，会给击毁enemytank的herotank回复一定的血量。

原理：由于每一个子弹都有自己的属性，当一个enemytank的血量<=0是，此坦克被击毁，根据最后一颗击毁它的子弹就可以判断出是player1还是player2击毁的了，再将其的血量增加一定得知即可。

#### 每局开始时的无敌时间

简介：在每次产生新的敌人的时候，就有一一定的无敌时间，此时即使herotank受到伤害，也不会掉血，而enemytank受到伤害仍然会掉血。

原理：此时需要用到System.cuurentTimeMills来获取从1970年到现在的时间，先初始化startTime,endTime，在generatenemy模块中，获取startTime，在paint（画图）模块中一直循环获取endTime，并且当endTime – startTime < GOODTIME（无敌时间）时，GOOD_flag = 1（处于无敌状态）；反之GOOD_flag = 0（不在无敌状态）。假若在无敌状态则在Missile class 中即使被enemytank的子弹击中，仍然不掉血，反之掉一定的血量。

#### 每局限制时间

简介：如上，要是一局的时间超过会定的时间，即超时，游戏仍将结束。

原理：在TankClient class中，若startTime < endTime – G_TIME, 则heroTank1.setDie(); heroTank2.setDie()。当heroTank1.islive() && heroTank2.live()时，才画屏幕。

#### 每局有一定概率产生血包

简介：每局会有一定的概率产生一个血包，并且血包的位置是随机的。在前几局中，产生血包的概率比较小，在之后的局数中产生的概率会增大。

原理：利用Random产生一定范围内的随机数，并把血包的所有可能位置存入一个数组中，通过随机数k作为数组的下标，保证在随机的位置产生血包。在generateEnemys模块中，利用随机数r.nextInt()产生一定范围内的随机数，并根据局数blood.freshLive()一定概率的产生新的血包。

#### 斜移

简介：同时按着上键或是右键，则往右上走。别切子弹也可以斜着移动。

原理：新建一个Direction class，用L, LU, U, RU, R, RC, D, LD 表示各个方向。通过keyPressed, keyReleased来检测键盘的处理，要是按下键，利用Switch， 则把相应的赋为true,否则为false。再在Tank class 中，通过switch， 若L，U为true, LU = true。If LU == true， 则x = x – speed; y = y + speed。八个反向全为false，dir = stop，坦克不动。

#### 改变成双人模式

简介：把担任的游戏改为双人的。

Player1：MOVE：W,S,A,D；	fire：J； super fire : SPACE

Player2：MOVE：UP,DOWN,LEFT,RIGHT； fire：ENTER；  super fire：CONTROL

原理：增加heroTank2.keyReleased，heroTank2.keyPressed，进而控制每一个坦克的移动、发炮。在Tank class 中增加player（player = true 为heroTank1;player1 = false 为heroTank2）。

#### 计算每个人的分数

简介：herotank击毁一个enemytank，效应的坦克的积分会增加。两个herotank之间是独立的。

原理：在Tank class 中新增Score，在Missile中判断要是enemytank.getlife() <= 0；则根据子弹的player属性，判断是给herotank1还是herotank2加分数（tc.heroTank1.setSocore(Score) ; tc.heroTank2.setScore(Score)）。

#### 每关增加难度

简介：在前五关，只会从屏幕的上方产生enemytank，数量每局会增加；在五到十关，enemytank将会从上方和下放出来，并且美观数量会增加；第十关之后，enemytank将会从屏幕的四边出来，并且每关坦克的数量会增加。

随着关数的增加，每关enemytank的发弹频率，以及移动的速度均会增加。并且每局的时间将会稍稍增加。

原理：在TankClient class 中增加ENEMYSIZE（可以用来间接的表示局数），在generateTank模块中产生新的enemyTank，根据拘束来计算出坦克产生的位置以及数量即可。在Tank class 中利用role判断是否为enemytank，是的话可以根据局数来适量的增加坦克的移动速度。ESPEED为enemytank的速度，可有ENEMYSIZE简介来表示。

#### 坦克全部阵亡，或是达到指定关数，就会自动跳出游戏

简介：两辆herotank阵亡，或是达到了G_END（获胜的局数），游戏自动结束，并在屏幕中央打出“恭喜大侠获胜”的字样。

原理：利用ENEMYSIZE间接表示局数，在TankClient class 中的paint中，要是局数 == G_END，则游戏结束，不再画任何图像。