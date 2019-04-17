package Sprites;

import Utility.Util;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static Utility.Constants.game;

public class Bitmask {

    public static HashMap<Integer, Integer> bitmaskMap = new HashMap<>(); //Maps bitmask to equivalent bitmask value (lots of cross-overs)

    public static HashMap<Integer, Integer> bitmaskImage = new HashMap<>(); //Maps bitmask to equivalent bitmask with 0 rotation
    public static HashMap<Integer, Integer> bitmaskRotation = new HashMap<>();  //Maps bitmask to rotation value from base

    private static int[] bottoms = new int[]{11, 22, 24, 8, 2, 16, 0, 26, 31, 30, 27, 10, 18};

    public static PImage generateImage(String sprite, int bitmask) {
        bitmask = bitmaskMap.get(bitmask); //get stripped mask

        String outline = sprite + "_OUTLINE";

        if(Util.contains(bottoms, bitmask)) sprite += "_BOTTOM";

        int rot = bitmaskRotation.get(bitmask);
        PImage mask = Sprites.mask.get(bitmaskImage.get(bitmask));

        PImage done = Util.maskImage(Sprites.tileSprites.get(sprite), Sprites.tileSprites.get(outline), mask, rot);
        done.save("/out/level/bitmask/completed.png");

        return done;
    }

    public static String bitmask(String sprite, int bitmask) {
        String newSprite = sprite + "_" + bitmaskMap.get(bitmask);

        if(Sprites.generatedMasks.getOrDefault(newSprite, null) == null) {
            Sprites.generatedMasks.put(newSprite, generateImage(sprite, bitmask));
        }
        return newSprite;
    }

    public static void init() {
        bitmaskMap = new HashMap<>();
        bitmaskImage = new HashMap<>();
        bitmaskRotation = new HashMap<>();

        //--T shapes--
        bitmaskMap.put(88, 88);
        bitmaskMap.put(89, 88);
        bitmaskMap.put(92, 88);
        bitmaskMap.put(93, 88);

        bitmaskMap.put(74, 74);
        bitmaskMap.put(78, 74);
        bitmaskMap.put(202, 74);
        bitmaskMap.put(206, 74);

        bitmaskMap.put(26, 26);
        bitmaskMap.put(154, 26);
        bitmaskMap.put(58, 26);
        bitmaskMap.put(186, 26);

        bitmaskMap.put(82, 82);
        bitmaskMap.put(114, 82);
        bitmaskMap.put(83, 82);
        bitmaskMap.put(115, 82);

        //--| shapes--
        bitmaskMap.put(248, 248);
        bitmaskMap.put(253, 248);
        bitmaskMap.put(249, 248);
        bitmaskMap.put(252, 248);

        bitmaskMap.put(107, 107);
        bitmaskMap.put(239, 107);
        bitmaskMap.put(111, 107);
        bitmaskMap.put(235, 107);

        bitmaskMap.put(31, 31);
        bitmaskMap.put(191, 31);
        bitmaskMap.put(159, 31);
        bitmaskMap.put(63, 31);

        bitmaskMap.put(214, 214);
        bitmaskMap.put(247, 214);
        bitmaskMap.put(246, 214);
        bitmaskMap.put(215, 214);

        //--b shapes--
        bitmaskMap.put(120, 120);
        bitmaskMap.put(121, 120);
        bitmaskMap.put(124, 120);
        bitmaskMap.put(125, 120);

        bitmaskMap.put(75, 75);
        bitmaskMap.put(79, 75);
        bitmaskMap.put(203, 75);
        bitmaskMap.put(207, 75);

        bitmaskMap.put(30, 30);
        bitmaskMap.put(158, 30);
        bitmaskMap.put(62, 30);
        bitmaskMap.put(190, 30);

        bitmaskMap.put(210, 210);
        bitmaskMap.put(242, 210);
        bitmaskMap.put(211, 210);
        bitmaskMap.put(243, 210);

        bitmaskMap.put(216, 216);
        bitmaskMap.put(217, 216);
        bitmaskMap.put(220, 216);
        bitmaskMap.put(221, 216);

        bitmaskMap.put(106, 106);
        bitmaskMap.put(110, 106);
        bitmaskMap.put(234, 106);
        bitmaskMap.put(238, 106);

        bitmaskMap.put(27, 27);
        bitmaskMap.put(155, 27);
        bitmaskMap.put(59, 27);
        bitmaskMap.put(187, 27);

        bitmaskMap.put(86, 86);
        bitmaskMap.put(118, 86);
        bitmaskMap.put(87, 86);
        bitmaskMap.put(119, 86);

        //-- L shapes--

        bitmaskMap.put(72, 72);
        bitmaskMap.put(205, 72);
        bitmaskMap.put(204, 72);
        bitmaskMap.put(77, 72);
        bitmaskMap.put(76, 72);
        bitmaskMap.put(201, 72);
        bitmaskMap.put(200, 72);
        bitmaskMap.put(73, 72);

        bitmaskMap.put(10, 10);
        bitmaskMap.put(174, 10);
        bitmaskMap.put(170, 10);
        bitmaskMap.put(142, 10);
        bitmaskMap.put(138, 10);
        bitmaskMap.put(46, 10);
        bitmaskMap.put(42, 10);
        bitmaskMap.put(14, 10);

        bitmaskMap.put(18, 18);
        bitmaskMap.put(179, 18);
        bitmaskMap.put(51, 18);
        bitmaskMap.put(178, 18);
        bitmaskMap.put(50, 18);
        bitmaskMap.put(147, 18);
        bitmaskMap.put(19, 18);
        bitmaskMap.put(146, 18);

        bitmaskMap.put(80, 80);
        bitmaskMap.put(117, 80);
        bitmaskMap.put(85, 80);
        bitmaskMap.put(113, 80);
        bitmaskMap.put(81, 80);
        bitmaskMap.put(116, 80);
        bitmaskMap.put(84, 80);
        bitmaskMap.put(112, 80);

        //--[] shapes--
        bitmaskMap.put(104, 104);
        bitmaskMap.put(237, 104);
        bitmaskMap.put(236, 104);
        bitmaskMap.put(109, 104);
        bitmaskMap.put(108, 104);
        bitmaskMap.put(233, 104);
        bitmaskMap.put(232, 104);
        bitmaskMap.put(105, 104);

        bitmaskMap.put(11, 11);
        bitmaskMap.put(175, 11);
        bitmaskMap.put(171, 11);
        bitmaskMap.put(143, 11);
        bitmaskMap.put(139, 11);
        bitmaskMap.put(47, 11);
        bitmaskMap.put(43, 11);
        bitmaskMap.put(15, 11);

        bitmaskMap.put(22, 22);
        bitmaskMap.put(183, 22);
        bitmaskMap.put(55, 22);
        bitmaskMap.put(182, 22);
        bitmaskMap.put(54, 22);
        bitmaskMap.put(151, 22);
        bitmaskMap.put(23, 22);
        bitmaskMap.put(150, 22);

        bitmaskMap.put(208, 208);
        bitmaskMap.put(245, 208);
        bitmaskMap.put(213, 208);
        bitmaskMap.put(241, 208);
        bitmaskMap.put(209, 208);
        bitmaskMap.put(244, 208);
        bitmaskMap.put(212, 208);
        bitmaskMap.put(240, 208);

        //-- - shapes --
        bitmaskMap.put(24, 24);
        bitmaskMap.put(189, 24);
        bitmaskMap.put(188, 24);
        bitmaskMap.put(157, 24);
        bitmaskMap.put(185, 24);
        bitmaskMap.put(61, 24);
        bitmaskMap.put(156, 24);
        bitmaskMap.put(57, 24);
        bitmaskMap.put(60, 24);
        bitmaskMap.put(184, 24);
        bitmaskMap.put(29, 24);
        bitmaskMap.put(153, 24);
        bitmaskMap.put(25, 24);
        bitmaskMap.put(28, 24);
        bitmaskMap.put(152, 24);
        bitmaskMap.put(56, 24);

        bitmaskMap.put(66, 66);
        bitmaskMap.put(231, 66);
        bitmaskMap.put(227, 66);
        bitmaskMap.put(230, 66);
        bitmaskMap.put(103, 66);
        bitmaskMap.put(199, 66);
        bitmaskMap.put(226, 66);
        bitmaskMap.put(71, 66);
        bitmaskMap.put(195, 66);
        bitmaskMap.put(99, 66);
        bitmaskMap.put(198, 66);
        bitmaskMap.put(102, 66);
        bitmaskMap.put(70, 66);
        bitmaskMap.put(194, 66);
        bitmaskMap.put(98, 66);
        bitmaskMap.put(67, 66);

        //-- ] shapes--
        bitmaskMap.put(8, 8);
        bitmaskMap.put(173, 8);
        bitmaskMap.put(169, 8);
        bitmaskMap.put(45, 8);
        bitmaskMap.put(172, 8);
        bitmaskMap.put(141, 8);
        bitmaskMap.put(168, 8);
        bitmaskMap.put(44, 8);
        bitmaskMap.put(137, 8);
        bitmaskMap.put(13, 8);
        bitmaskMap.put(140, 8);
        bitmaskMap.put(136, 8);
        bitmaskMap.put(12, 8);
        bitmaskMap.put(41, 8);
        bitmaskMap.put(9, 8);
        bitmaskMap.put(40, 8);

        bitmaskMap.put(2, 2);
        bitmaskMap.put(167, 2);
        bitmaskMap.put(39, 2);
        bitmaskMap.put(135, 2);
        bitmaskMap.put(163, 2);
        bitmaskMap.put(166, 2);
        bitmaskMap.put(35, 2);
        bitmaskMap.put(131, 2);
        bitmaskMap.put(38, 2);
        bitmaskMap.put(134, 2);
        bitmaskMap.put(162, 2);
        bitmaskMap.put(34, 2);
        bitmaskMap.put(130, 2);
        bitmaskMap.put(7, 2);
        bitmaskMap.put(6, 2);
        bitmaskMap.put(3, 2);

        bitmaskMap.put(16, 16);
        bitmaskMap.put(181, 16);
        bitmaskMap.put(149, 16);
        bitmaskMap.put(180, 16);
        bitmaskMap.put(53, 16);
        bitmaskMap.put(177, 16);
        bitmaskMap.put(21, 16);
        bitmaskMap.put(52, 16);
        bitmaskMap.put(145, 16);
        bitmaskMap.put(176, 16);
        bitmaskMap.put(49, 16);
        bitmaskMap.put(17, 16);
        bitmaskMap.put(48, 16);
        bitmaskMap.put(148, 16);
        bitmaskMap.put(144, 16);
        bitmaskMap.put(20, 16);

        bitmaskMap.put(64, 64);
        bitmaskMap.put(229, 64);
        bitmaskMap.put(228, 64);
        bitmaskMap.put(225, 64);
        bitmaskMap.put(197, 64);
        bitmaskMap.put(101, 64);
        bitmaskMap.put(196, 64);
        bitmaskMap.put(193, 64);
        bitmaskMap.put(100, 64);
        bitmaskMap.put(97, 64);
        bitmaskMap.put(69, 64);
        bitmaskMap.put(68, 64);
        bitmaskMap.put(65, 64);
        bitmaskMap.put(224, 64);
        bitmaskMap.put(96, 64);
        bitmaskMap.put(192, 64);

        //-- . shapes--
        bitmaskMap.put(0, 0);
        bitmaskMap.put(165, 0);
        bitmaskMap.put(161, 0);
        bitmaskMap.put(33, 0);
        bitmaskMap.put(1, 0);
        bitmaskMap.put(36, 0);
        bitmaskMap.put(37, 0);
        bitmaskMap.put(5, 0);
        bitmaskMap.put(4, 0);
        bitmaskMap.put(129, 0);
        bitmaskMap.put(133, 0);
        bitmaskMap.put(132, 0);
        bitmaskMap.put(128, 0);
        bitmaskMap.put(164, 0);
        bitmaskMap.put(160, 0);
        bitmaskMap.put(32, 0);

        //-- Full shape--
        bitmaskMap.put(255, 255);

        //-- Corner missing--
        bitmaskMap.put(254, 254);
        bitmaskMap.put(251, 251);
        bitmaskMap.put(127, 127);
        bitmaskMap.put(223, 223);

        bitmaskMap.put(250, 250);
        bitmaskMap.put(123, 123);
        bitmaskMap.put(95, 95);
        bitmaskMap.put(222, 222);

        bitmaskMap.put(122, 122);
        bitmaskMap.put(91, 91);
        bitmaskMap.put(94, 94);
        bitmaskMap.put(218, 218);

        bitmaskMap.put(126, 126);
        bitmaskMap.put(219, 219);

        bitmaskMap.put(90, 90);

        //---ROTATIONS---
        bitmaskRotation.put(255, 0);
        bitmaskRotation.put(90, 0);
        bitmaskRotation.put(0, 0);

        bitmaskRotation.put(251, 0);
        bitmaskRotation.put(127, 1);
        bitmaskRotation.put(223, 2);
        bitmaskRotation.put(254, 3);

        bitmaskRotation.put(123, 0);
        bitmaskRotation.put(95, 1);
        bitmaskRotation.put(222, 2);
        bitmaskRotation.put(250, 3);

        bitmaskRotation.put(91, 0);
        bitmaskRotation.put(94, 1);
        bitmaskRotation.put(218, 2);
        bitmaskRotation.put(122, 3);

        bitmaskRotation.put(219, 0);
        bitmaskRotation.put(126, 1);

        bitmaskRotation.put(82, 0);
        bitmaskRotation.put(88, 1);
        bitmaskRotation.put(74, 2);
        bitmaskRotation.put(26, 3);

        bitmaskRotation.put(214, 0);
        bitmaskRotation.put(248, 1);
        bitmaskRotation.put(107, 2);
        bitmaskRotation.put(31, 3);

        bitmaskRotation.put(210, 0);
        bitmaskRotation.put(120, 1);
        bitmaskRotation.put(75, 2);
        bitmaskRotation.put(30, 3);

        bitmaskRotation.put(86, 0);
        bitmaskRotation.put(216, 1);
        bitmaskRotation.put(106, 2);
        bitmaskRotation.put(27, 3);

        bitmaskRotation.put(18, 0);
        bitmaskRotation.put(80, 1);
        bitmaskRotation.put(72, 2);
        bitmaskRotation.put(10, 3);

        bitmaskRotation.put(22, 0);
        bitmaskRotation.put(208, 1);
        bitmaskRotation.put(104, 2);
        bitmaskRotation.put(11, 3);

        bitmaskRotation.put(66, 0);
        bitmaskRotation.put(24, 1);

        bitmaskRotation.put(2, 0);
        bitmaskRotation.put(16, 1);
        bitmaskRotation.put(64, 2);
        bitmaskRotation.put(8, 3);

        //---Images---
        bitmaskImage.put(255, 255);
        bitmaskImage.put(90, 90);
        bitmaskImage.put(0, 0);

        bitmaskImage.put(251, 251);
        bitmaskImage.put(127, 251);
        bitmaskImage.put(223, 251);
        bitmaskImage.put(254, 251);

        bitmaskImage.put(123, 123);
        bitmaskImage.put(95, 123);
        bitmaskImage.put(222, 123);
        bitmaskImage.put(250, 123);

        bitmaskImage.put(91, 91);
        bitmaskImage.put(94, 91);
        bitmaskImage.put(218, 91);
        bitmaskImage.put(122, 91);

        bitmaskImage.put(219, 219);
        bitmaskImage.put(126, 219);

        bitmaskImage.put(82, 82);
        bitmaskImage.put(88, 82);
        bitmaskImage.put(74, 82);
        bitmaskImage.put(26, 82);

        bitmaskImage.put(214, 214);
        bitmaskImage.put(248, 214);
        bitmaskImage.put(107, 214);
        bitmaskImage.put(31, 214);

        bitmaskImage.put(210, 210);
        bitmaskImage.put(120, 210);
        bitmaskImage.put(75, 210);
        bitmaskImage.put(30, 210);

        bitmaskImage.put(86, 86);
        bitmaskImage.put(216, 86);
        bitmaskImage.put(106, 86);
        bitmaskImage.put(27, 86);

        bitmaskImage.put(18, 18);
        bitmaskImage.put(80, 18);
        bitmaskImage.put(72, 18);
        bitmaskImage.put(10, 18);

        bitmaskImage.put(22, 22);
        bitmaskImage.put(208, 22);
        bitmaskImage.put(104, 22);
        bitmaskImage.put(11, 22);

        bitmaskImage.put(66, 66);
        bitmaskImage.put(24, 66);

        bitmaskImage.put(2, 2);
        bitmaskImage.put(16, 2);
        bitmaskImage.put(64, 2);
        bitmaskImage.put(8, 2);
    }
}
