<script>
export default {
  name: 'MenuItem',
  // functional: true,
  props: {
    rightIcon: {
      type: String,
      default: '',
    },
    icon: {
      type: String,
      default: '',
    },
    title: {
      type: String,
      default: '',
    },
  },
  render(h) {
    const { icon, title, rightIcon } = this.$props;
    const vnodes = [];
    const SPAN = h(
      'span',
      {
        on: {
          click: v => {
            this.$emit('titleClick', v);
          },
        },
      },
      title,
    );
    const I = icon => {
      return h('i', {
        class: [icon, 'sub-el-icon'],
        on: {
          click: v => {
            this.$emit('iconClick', v);
          },
        },
      });
    };
    const SVG = icon => {
      return h('svg-icon', {
        props: {
          'icon-class': icon,
        },
        class: 'sub-el-icon',
        on: {
          click: v => {
            this.$emit('iconClick', v);
          },
        },
      });
    };

    if (icon) {
      if (icon.includes('el-icon')) {
        vnodes.push(I(icon));
      } else {
        vnodes.push(SVG(icon));
      }
    }

    if (title) {
      vnodes.push(SPAN);
    }

    if (rightIcon) {
      if (rightIcon.includes('el-icon')) {
        vnodes.push(I(rightIcon));
      } else {
        vnodes.push(SVG(rightIcon));
      }
    }
    return h(
      'div',
      {
        class: {
          'menu-item-div': true,
          'right-icon': !!rightIcon,
        },
      },
      vnodes,
    );
  },
};
</script>

<style lang="scss" scoped>
.sub-el-icon {
  color: currentColor;
  width: 1em;
  height: 1em;
  font-size: 18px;
}
.menu-item-div > * {
  cursor: pointer;
  -moz-user-select: none; /*火狐*/
  -webkit-user-select: none; /*webkit浏览器*/
  -ms-user-select: none; /*IE10*/
  -khtml-user-select: none; /*早期浏览器*/
  user-select: none;
}
.menu-item-div.right-icon {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;

  i {
    font-size: 14px;
    color: $main-dark;
  }
}

.menu-item-div.right-icon i.el-icon-star-off {
  display: none;
}
.title-item:hover .menu-item-div.right-icon i.el-icon-star-off {
  display: inline-block;
}
</style>
