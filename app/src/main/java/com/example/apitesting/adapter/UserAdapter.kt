package com.example.apitesting.adapter

import android.app.Activity
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apitesting.R
import com.example.apitesting.data.model.User
import com.example.apitesting.databinding.LayoutUsersListBinding
import com.example.testingapp.utils.Constants.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class UserAdapter(private val requireActivity:Activity, private val buttonContinue:Button) : PagingDataAdapter<User, UserAdapter.ViewHolder>
    (

    object : DiffUtil.ItemCallback<User>()
    {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean
        {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean
        {
            return oldItem.toString() == newItem.toString()
        }
    } // object diffUtil closed
) , ActionMode.Callback
{

    private var multiSelection = false
    private var selectedUser = arrayListOf<User>()
    private var selectedUsersPosition = arrayListOf<Int>()
    private var myViewHolders = arrayListOf<ViewHolder>()
    private lateinit var actionMode:ActionMode





    inner class  ViewHolder(view: LayoutUsersListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutUsersListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)

    } // onCreateViewHolder closed

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val user = getItem(position)
        Log.d(TAG, "onBindViewHolder: "+position)

        saveItemSelectionOnScroll(holder,position)
        myViewHolders.add(holder)

        LayoutUsersListBinding.bind(holder.itemView).apply()
        {
            textViewLayoutUsersUserName.text = user?.username
        } // binding closed

        holder.itemView.setOnClickListener()
        {
            if(!multiSelection)
            {
                requireActivity.startActionMode(this)
                applySelection(holder,user!!,position)
                multiSelection = true
            }
            else
            {
                applySelection(holder,user!!,position)
            }

        } // onClick Listener closed


        buttonContinue.setOnClickListener()
        {

                selectedUser.forEach()
                {
                    user ->
                    Log.d(TAG, "onBindViewHolder: "+user.toString())
                } // forEach closed

                multiSelection = false
                selectedUser.clear()
                actionMode.finish()
                buttonContinue.isEnabled = false
        } // buttonContinue close

    } // onBindViewHolder closed


    fun applyStatusBarColor(color:Int)
    {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity,color)
    } //

    fun saveItemSelectionOnScroll(holder: ViewHolder,currentPosition:Int)
    {
        if(selectedUsersPosition.contains(currentPosition))
        {
            changeUserStyle(holder,R.color.colorPrimary,R.color.white)
        } // if closed
        else
        {
            changeUserStyle(holder,R.color.white,R.color.black)
        } // else closed

    } // saveItemSelectionOnScroll closed


    fun applySelection(holder: ViewHolder,currentUser:User,position: Int)
    {
        if(selectedUser.contains(currentUser))
        {
            selectedUser.remove(currentUser)
            selectedUsersPosition.remove(position)
            changeUserStyle(holder,R.color.white,R.color.black)
            applyActionModeTitle()
        } // if closed
        else
        {
            selectedUser.add(currentUser)
            selectedUsersPosition.add(position)
            changeUserStyle(holder,R.color.colorPrimary,R.color.white)
            applyActionModeTitle()
        } // else closed
    } // applySelection closed

    private fun changeUserStyle(holder: ViewHolder,color: Int,txtColor:Int)
    {
        CoroutineScope(Dispatchers.Main).launch()
        {
            LayoutUsersListBinding.bind(holder.itemView).apply ()
            {
                relativeLayoutUsers.setBackgroundColor(ContextCompat.getColor(requireActivity,color))
                textViewLayoutUsersUserName.setTextColor(ContextCompat.getColor(requireActivity,txtColor))
            } // LayoutRecipesItemsRowBinding

        } // couroutine closed


    } // changeRecipeStyle



    private fun applyActionModeTitle()
    {
        when(selectedUser.size)
        {
            0 ->
            {
                actionMode.finish()
                multiSelection = false
            }
            1-> actionMode.title = "${selectedUser.size} item selected"

            else ->
            {
                actionMode.title = "${selectedUser.size} item selected"
            }
        } // when closed
    } // actionModeTitle closed



    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean
    {

        applyStatusBarColor(R.color.contextualStatusBarColor)
        actionMode = mode!!
        buttonContinue.isEnabled = true
        return  true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean
    {
        return  true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean
    {

        return  true
    }

    override fun onDestroyActionMode(mode: ActionMode?)
    {
        multiSelection = false
        selectedUser.clear();
        applyStatusBarColor(R.color.purple_700)
        myViewHolders.forEach()
        {
            holder ->
            changeUserStyle(holder,R.color.white,R.color.black)
        } //
    } // onDestroyActionMode closed




} // UserAdapter closed